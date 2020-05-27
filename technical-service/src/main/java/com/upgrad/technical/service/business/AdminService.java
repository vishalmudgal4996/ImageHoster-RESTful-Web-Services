package com.upgrad.technical.service.business;


import com.upgrad.technical.service.dao.ImageDao;
import com.upgrad.technical.service.dao.UserDao;
import com.upgrad.technical.service.entity.ImageEntity;
import com.upgrad.technical.service.entity.UserAuthTokenEntity;
import com.upgrad.technical.service.exception.ImageNotFoundException;
import com.upgrad.technical.service.exception.UnauthorizedException;
import com.upgrad.technical.service.exception.UserNotSignedInException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;

@Service
public class AdminService {

    @Autowired
    private ImageDao imageDao;

    public ImageEntity getImage(final String imageUuid, final String authorization) throws ImageNotFoundException, UnauthorizedException, UserNotSignedInException {

        UserAuthTokenEntity userAuthTokenEntity = imageDao.getUserAuthToken(authorization);

        if (userAuthTokenEntity == null) {
            throw new UserNotSignedInException("USR-001", "You are not Signed in, sign in first to get the details of the image");
        }

        String role = userAuthTokenEntity.getUser().getRole();
        if (role.equals("admin")) {
            ImageEntity imageEntity = imageDao.getImage(imageUuid);
            if (imageEntity == null) {
                throw new ImageNotFoundException("IMG-001", "Image with Uuid not found");
            }
            return imageEntity;
        } else
            throw new UnauthorizedException("ATH-001", "UNAUTHORIZED Access, Entered user is not an admin");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public ImageEntity updateImage(final ImageEntity imageEntity, final String authorization) throws ImageNotFoundException, UnauthorizedException, UserNotSignedInException {
        //Complete this method
        //Firstly check whether the access token is a valid one(exists in user_auth_tokens table). If not valid throw UserNotSignedException
        UserAuthTokenEntity userAuthTokenEntity = imageDao.getUserAuthToken(authorization);

        if (userAuthTokenEntity == null) {
            throw new UserNotSignedInException("USR-001", "You are not Signed in, sign in first to get the details of the image");
        }

        //Then check the role of the user with entered access token (if nonadmin then throw UnauthorizedException)
        //If the role is admin, get the existing image in the database with entered image id using getImageById() method in ImageDao class
        //If the image with entered image id does not exist throw ImageNotFoundException
        //If the image with entered image id exists in the database and is returned, try to set all the attributes of the new image(received by this method) using the existing image
        //Call updateImage() method for imageDao to update an image
        //Note that ImageNotFoundException , UserNotFoundException and UnauthorizedException has been implemented
        //Note that this method returns ImageEntity type object

        String role = userAuthTokenEntity.getUser().getRole();
        if (role.equals("admin")) {

            ImageEntity existingimageEntity = imageDao.getImageById(imageEntity.getId());
            if(existingimageEntity==null){
                throw new ImageNotFoundException("IMG-001", "Image with id not found");
            }

            imageEntity.setUuid(existingimageEntity.getUuid());
            imageEntity.setNo_of_likes(existingimageEntity.getNo_of_likes());
            imageEntity.setUser_id(existingimageEntity.getUser_id());
            imageEntity.setCreated_at(existingimageEntity.getCreated_at());

            return imageDao.updateImage(imageEntity);
        } else
            throw new UnauthorizedException("ATH-001", "UNAUTHORIZED Access, Entered user is not an admin");
    }
}
