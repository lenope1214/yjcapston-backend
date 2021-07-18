package com.yjwdb2021.jumanji.service;

import com.yjwdb2021.jumanji.config.jwt.JwtResponse;
import com.yjwdb2021.jumanji.config.jwt.JwtTokenUtil;
import com.yjwdb2021.jumanji.data.User;
import com.yjwdb2021.jumanji.repository.UserRepository;
import com.yjwdb2021.jumanji.service.exception.auth.ForbiddenException;
import com.yjwdb2021.jumanji.service.exception.userException.LoginFailedException;
import com.yjwdb2021.jumanji.service.exception.userException.UserHasExistException;
import com.yjwdb2021.jumanji.service.exception.userException.UserNotFoundException;
import com.yjwdb2021.jumanji.service.interfaces.BasicService;
import com.yjwdb2021.jumanji.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    HttpHeaders httpHeaders;

    @Autowired
    ShopServiceImpl shopService;

    @Transactional
    public String getMyId(String authorization) {
        return jwtTokenUtil.getUsername(authorization);
    }

    @Transactional
    public User get(String id) {
        return isPresent(id);
    }

    @Override
    @Transactional
    public List<User> getList(String authorization) {
        return userRepository.findAll();
    }

    @Transactional
    public User post(User.Request request) {
        isEmpty(request.getId());
        String rawPassword = request.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        User userEntity = User.createUser()
                .id(request.getId())
                .password(encPassword)
                .name(request.getName())
                .phone(request.getPhone())
                .email(request.getEmail())
                .sign_date(new Date())
                .birthday(request.getBirthday())
                .address(request.getAddress())
                .addressDetail(request.getAddressDetail())
                .role(request.getRole())
                .provider("jumin") /** 얘는 추후에 변경해야함. **/
                .provider_id(null)
//                .level(0)
                .deviceToken(request.getDeviceToken())
                .build();
        return userRepository.save(userEntity);
    }

    @Override
    @Transactional
    public User patch(String authorization, User.Request request) {
        String loginId = getMyId(authorization);
        isPresent(loginId);

        User loginUser = userRepository.findById(loginId).get();
        updateInfo(loginUser, request);
        return loginUser;
    }

    @Transactional
    public void delete(String authorization) {
        String loginId = getMyId(authorization);


        User user = isPresent(loginId);
        user.setIsWdrw('Y');
        userRepository.save(user);
    }

    @Transactional
    public ResponseEntity<?> login(User.Request request) {

        User userEntity = isPresent(request.getId());
//         아이디 오류 후에 아이디, 비번 오류 통합.. 현재는 있는지 확인하기 위해 이렇게 둠.
        checkPW(request, userEntity.getPassword());
//        isWdrw(request.getId());
        final String access_token = jwtTokenUtil.generateToken(userEntity.getId());
        JwtResponse jwtResponse = new JwtResponse(access_token, userEntity.getRole());
        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
    }

    @Transactional
    public JwtResponse oAuthLogin(User.Request request) {
        // oauth 로그인은 uid를 받게되고 provider랑 uid를 통해서 해당 아이디 체크.
        // 없으면 회원가입 진행 있으면 로그인

        Optional<User> userOptional =
                userRepository.findByProviderAndProviderId(request.getProvider(), request.getProviderId());

        User user;
        if (userOptional.isPresent()) {
            user = userOptional.get();
            // user가 존재하면 update 해주기
            //user.setEmail(oAuth2UserInfo.getEmail());
//            userRepository.save(user);
        } else {

            // user의 패스워드가 null이기 때문에 OAuth 유저는 일반적인 로그인을 할 수 없음.
            user = User.createUser()
                    .id(request.getProvider() + "_" + request.getProviderId())
                    .name(request.getName())
                    .email(request.getEmail())
                    .role("ROLE_USER")
                    .provider(request.getProvider())
                    .provider_id(request.getProviderId())
                    .phone(request.getPhone())
                    .address(request.getAddress())
                    .addressDetail(request.getAddressDetail())
                    .birthday(request.getBirthday())
                    .sign_date(new Date())
                    .build();
            userRepository.save(user);
        }

        final String access_token = jwtTokenUtil.generateToken(user.getId());
        JwtResponse jwtResponse = new JwtResponse(access_token, user.getRole());
        return jwtResponse;
    }

    public void checkPW(User.Request _user, String encodedPassword) {
        String rawPassword = _user.getPassword();
        System.out.println("rawPw : " + rawPassword);
        System.out.println("encPw : " + encodedPassword);
        if (!bCryptPasswordEncoder.matches(rawPassword, encodedPassword))
            throw new LoginFailedException();
    }

    public void updateInfo(User user, User.Request newData) {
        System.out.println("Update User in");
        if (newData.getEmail() != null) user.setEmail(newData.getEmail());
        if (newData.getAddress() != null) user.setAddress(newData.getAddress());
        if (newData.getAddressDetail() != null) user.setAddressDetail(newData.getAddressDetail());
        if (newData.getPassword() != null) user.setPassword(bCryptPasswordEncoder.encode(newData.getPassword()));
        userRepository.save(user);
    }

    public boolean isEmpty(String id) {
        if (userRepository.findById(id).isEmpty()) return true;
        throw new UserHasExistException(id);
    }


    public User isPresent(String id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent() && user.get().getIsWdrw() != 'Y') return user.get();
        throw new UserNotFoundException(id);
    }

//    public void isWdrw(String id) {
//        if (userRepository.findById(id).get().getIsWdrw() == 'Y')
//            throw new UserNotFoundException();
//    }

    /**
     * role args ex) "ADMIN"  "OWNER"  "USER"
     **/
    public void isAuth(String userRole, String role) {
        if(!authCheck(userRole, role))throw new ForbiddenException();
    }

    public boolean authCheck(String userRole, String role){
        if (userRole.equals("ROLE_ADMIN")) return true; // 어드민은 무조건 가능.
        if (userRole.equals("ROLE_OWNER")) return !role.equals("ADMIN"); // OWNER는 어드민 권한만 아니면 다 가능.
        if (userRole.equals("ROLE_USER")) return role.equals("USER"); // 유저는 유저만 가능.
        throw new ForbiddenException();// ;
    }

    public User isLogin(String authorization){
        return isPresent(getMyId(authorization));
    }

    public String getDeviceToken(String userId) {
        return isPresent(userId).getDeviceToken();
    }

    public void save(User user) {
        userRepository.save(user);
    }
}