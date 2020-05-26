package com.upgrad.technical.service.dao;

import com.upgrad.technical.service.entity.UserAuthTokenEntity;
import com.upgrad.technical.service.entity.UserEntity;
import com.upgrad.technical.service.entity.UserEntity;
import jdk.nashorn.internal.objects.NativeObject;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Repository
public class UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    public UserEntity createUser(UserEntity userEntity) {
        entityManager.persist(userEntity);
        return  userEntity;
    }

    public UserEntity getUserByEmail(final String email) {
        //Complete this method
        try{

            return entityManager.createNamedQuery("userByEmail" , UserEntity.class)
                    .setParameter("email" , email).getSingleResult();
        }catch (NoResultException noResultException){
            return null;
        }
        //Modify this method to catch the "NoResultException" when the query returns no record, and hence return null from catch block
    }

    public UserAuthTokenEntity createAuthToken(final UserAuthTokenEntity userAuthTokenEntity) {
        entityManager.persist(userAuthTokenEntity);
        return userAuthTokenEntity;
    }

    public void updateUser(final UserEntity updatedUserEntity) {
        entityManager.merge(updatedUserEntity);
    }
}
