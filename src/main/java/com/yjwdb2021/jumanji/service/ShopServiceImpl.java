package com.yjwdb2021.jumanji.service;

import com.yjwdb2021.jumanji.data.DateOperator;
import com.yjwdb2021.jumanji.data.Shop;
import com.yjwdb2021.jumanji.data.User;
import com.yjwdb2021.jumanji.data.UserShopMark;
import com.yjwdb2021.jumanji.repository.ShopRepository;
import com.yjwdb2021.jumanji.repository.UserRepository;
import com.yjwdb2021.jumanji.repository.UserShopMarksRepository;
import com.yjwdb2021.jumanji.service.exception.auth.ForbiddenException;
import com.yjwdb2021.jumanji.service.exception.shopException.ShopHasExistException;
import com.yjwdb2021.jumanji.service.exception.shopException.ShopNotFoundException;
import com.yjwdb2021.jumanji.service.interfaces.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {
    @Autowired
    ShopRepository shopRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserServiceImpl userService;
    @Autowired
    StorageServiceImpl storageService;
    private final UserShopMarksRepository usmRepository;
//    @Autowired
//    S3Uploader s3Uploader;


    public List<Shop> getShopListByOwnerId(String id) {
        List<Shop> shopList = shopRepository.findAllByOwner_Id(id);
        if (shopList.size() == 0) throw new ShopNotFoundException();
        return shopList;
    }

    public char reverseIsOpen(Shop shop) {
        if (shop.getIsOpen() == 'Y') shop.setIsOpen('N');
        else shop.setIsOpen('Y');
        shopRepository.save(shop);
        return shop.getIsOpen();
    }

    public char reverseIsRsPos(Shop shop) {
        if (shop.getIsRsPos() == 'Y') shop.setIsRsPos('N');
        else shop.setIsRsPos('Y');
        shopRepository.save(shop);
        return shop.getIsRsPos();
    }


    public char patchShopIsOpen(String authorization, String shopId) {
        String loginId = userService.getMyId(authorization);
        isOwnShop(loginId, shopId);
        Shop shop = isPresent(shopId);
        return reverseIsOpen(shop);
    }

    public char patchSHopIsRsPos(String authorization, String shopId) {
        String loginId = userService.getMyId(authorization);
        isOwnShop(loginId, shopId);
        Shop shop = isPresent(shopId);
        return reverseIsRsPos(shop);
    }

    public Shop getShopByShopId(@Nullable String authorization, String shopId) {
        // 변수
        Shop shop;

        // 값 확인 - 디버그로 하기.

        // 서비스

        shop = isPresent(shopId);

//        if(shop.getImgPath()!=null)response.setImg(storageService.loadImg(shop.getImgPath()));
        return shop;
    }


    public List<Shop> getMyShop(String authorization) {
        System.out.println("ShopController in getMyShop");
        String loginId = userService.getMyId(authorization);
        User userEntity = userRepository.findById(loginId).get();
        List<Shop> myShopList = getShopListByOwnerId(userEntity.getId());
        return myShopList;
    }


    @Override
    public List<Shop.Dao> getList(String category, String sortTarget) {
        List<Shop> shopList = new ArrayList<>();
        sortTarget = sortTarget == null ? "" : sortTarget;
        category = category == null ? "" : category;
        List<Shop.Dao> daoList = shopRepository.getShopListByCategorySortTarget(category, sortTarget);
        return daoList;
    }

    @Override
    public Shop post(String authorization, Shop.PostRequest request) {
        System.out.println("매장등록's shopId : " + request.getShopId());

        String loginId = userService.getMyId(authorization);
        User user = userService.isPresent(loginId);
        String uri = "shop/" + request.getShopId() + "/thumbnail/";
        String imgPath = null;
        System.out.println("openTime : " + request.getOpenTime());
        System.out.println("closeTime : " + request.getCloseTime());

        // 유효성 검사
        isEmpty(request.getShopId()); // 해당 사업자 번호로 사업자 등록이 됐는지 확인 비어있어야 등록
        userService.isPresent(loginId); // 해당 요청하는 사람이 존재하는지
        userService.isAuth(user.getRole(), "OWNER");


        if (request.getImg() != null && request.getImg().getSize() > 0) {
//            System.out.println("imgInfo >>>>>>>>>>>>>>>>>>>>>>>>>");
//            System.out.println(request.getImg().getResource().getFilename());
//            System.out.println(request.getImg().getResource().getFilename().replace(" ", "_"));
//            System.out.println(request.getImg().getName());
//            System.out.println("imgInfo >>>>>>>>>>>>>>>>>>>>>>>>>");
            imgPath = storageService.store(request.getImg(), request.getImg().getResource().getFilename().replace(" ", "_"), uri.split("/"));
//            try {
//                s3Uploader.upload(request.getImg(), imgPath);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
        Date openTime = DateOperator.stringToMilisecond(request.getOpenTime());
        Date closeTime = DateOperator.stringToMilisecond(request.getCloseTime());
        Shop shopEntity;
        shopEntity = Shop.insertShop()
                .id(request.getShopId())
                .name(request.getName().replace(" ", "_"))
                .intro(request.getIntro())
                .openTime(openTime)
                .closeTime(closeTime)
                .address(request.getAddress())
                .addressDetail(request.getAddressDetail())
                .category(request.getCategory())
                .imgPath(imgPath)
                .owner(user)
                .phone(request.getPhone())
                .build();
        Shop shop = shopRepository.save(shopEntity);
        return shop;
    }

    @Override
    public Shop patch(String authorization, Shop.PatchRequest request) {
        System.out.println("patch.getShopId() : " + request.getShopId());
        String userId = userService.getMyId(authorization);
        Shop shop = shopRepository.findById(request.getShopId()).get();
        isPresent(request.getShopId());
        isOwnShop(userId, shop.getId());


        System.out.println(
                "매장 수정 patch.toString()" + "\n" +
                        "patch.getOpenTime : " + request.getOpenTime() + "\n" +
                        "patch.getCloseTime : " + request.getCloseTime()
        );
        shop.update(request);
        return shop;
    }

    @Override
    public void delete(String authorization, String shopId) {
        String loginId = userService.getMyId(authorization); // jwt가 있다는 것은 유저 인증이 완료. isPresent 필요 없음.
        isOwnShop(loginId, shopId);
        Shop shopEntity = isPresent(shopId);
        shopRepository.delete(shopEntity);
    }

    // 있는 매장번호 인지 확인. 없으면 error를 반환하게 된다.
    public Shop isPresent(String shopId) {
        Optional<Shop> shop = shopRepository.findById(shopId);
        if (shop.isPresent()) return shop.get();
        throw new ShopNotFoundException();
    }

    public boolean isEmpty(String shopId) {
        if (shopRepository.findById(shopId).isEmpty()) return true;
        throw new ShopHasExistException();
    }

    /**
     * @param loginId : 유저가 로그인한 아이디
     * @param shopId  : 입력된 매장 아이디
     * @return 내 매장이면 true, 아니면 권한없음 에러가 던져진다.
     */
    public void isOwnShop(String loginId, String shopId) {
        System.out.println("보유매장 비교 유저아디 : " + loginId);
        System.out.println("보유매장 비교 매장번호 : " + shopId);
        User loginUser = userService.get(loginId);
        System.out.println("로긘 유저 이름 : " + loginUser.getName());
        for (Shop shop : shopRepository.findByOwnerId(loginId)) {
            if (shop.getId().equals(shopId)) {
                System.out.println("보유매장 매칭된 매장번호 : " + shop.getId());
                return;
            }
        }
        throw new ForbiddenException();
    }

    public int getShopListSize() {
        return shopRepository.findAll().size();
    }

    public String toOracleChar(String str) {
        return "'" + str + "'";
    }

//    public String getDeviceToken(String shopId) {
//
//    }
}