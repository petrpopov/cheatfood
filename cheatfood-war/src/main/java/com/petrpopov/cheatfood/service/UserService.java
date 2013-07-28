package com.petrpopov.cheatfood.service;

import com.petrpopov.cheatfood.config.CheatException;
import com.petrpopov.cheatfood.model.UserCreate;
import com.petrpopov.cheatfood.model.UserEntity;
import com.petrpopov.cheatfood.security.CheatPasswordEncoder;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.util.UUID;

/**
 * User: petrpopov
 * Date: 12.02.13
 * Time: 13:33
 */
@Component
public class UserService extends GenericService<UserEntity> {

    @Autowired
    @Qualifier("mongoTemplate")
    private MongoOperations op;

    @Autowired
    private CheatPasswordEncoder encoder;

    public UserService() {
        super(UserEntity.class);
    }

    @CacheEvict(value = "users", allEntries = true)
    public UserEntity createUser(@Valid UserCreate user) throws CheatException {

        UserEntity userByEmail = this.getUserByEmail(user.getEmail());

        if( userByEmail != null )
            throw new CheatException("User is already exists!");

        //generate random salt
        UUID randomUUID = UUID.randomUUID();
        String newSalt = randomUUID.toString();

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(user.getEmail());

        String encodePassword = encoder.encodePassword(user.getPassword(), newSalt);
        userEntity.setPasswordHash(encodePassword);
        userEntity.setSalt(newSalt);

        op.save(userEntity);
        return getUserByEmail(user.getEmail());
    }

    //@Cacheable("users")
    public UserEntity getUserByEmail(String email) {
        Criteria criteria = Criteria.where("email").is(email);
        Query query = new Query(criteria);

        return op.findOne(query, UserEntity.class);
    }

    @Cacheable(value = "users", key = "#id")
    public UserEntity getUserById(String id) {
        return this.findById(id);
    }

  //  @Cacheable("users")
    public UserEntity getUserByFoursquareId(String foursquareId)
    {
        Criteria criteria = Criteria.where("foursquareId").is(foursquareId);
        Query query = new Query(criteria);

        return op.findOne(query, UserEntity.class);
    }

    public UserEntity getUserByFoursquareTwitterUsername(String foursquareTwitterUsername) {
        Criteria criteria = Criteria.where("foursquareTwitterUsername").is(foursquareTwitterUsername);
        Query query = new Query(criteria);

        return op.findOne(query, UserEntity.class);
    }

    // @Cacheable("users")
    public UserEntity getUserByFacebookId(String facebookId) {
        Criteria criteria = Criteria.where("facebookId").is(facebookId);
        Query query = new Query(criteria);

        return op.findOne(query, UserEntity.class);
    }

    public UserEntity getUserByTwitterId(String twitterId) {
        Criteria criteria = Criteria.where("twitterId").is(twitterId);
        Query query = new Query(criteria);

        return op.findOne(query, UserEntity.class);
    }

    public UserEntity getUserByTwitterUsername(String twitterUsername) {
        Criteria criteria = Criteria.where("twitterUsername").is(twitterUsername);
        Query query = new Query(criteria);

        return op.findOne(query, UserEntity.class);
    }

    //  @Cacheable("users")
    public UserEntity getUserByCookieId(String cookie)
    {
        Criteria criteria = Criteria.where("cookieId").is(cookie);
        Query query = new Query(criteria);

        return op.findOne(query, UserEntity.class);
    }

    @CacheEvict(value = "users", allEntries = true) //key = "#userEntity.id")
    public UserEntity saveOrUpdate(UserEntity userEntity)
    {
        if(userEntity.getFoursquareId() != null) {
            return saveOrUpdateFoursquareUser(userEntity);
        }
        else if( userEntity.getFacebookId() != null ) {
            return saveOrUpdateFacebookUser(userEntity);
        }
        else if( userEntity.getTwitterId() != null ) {
            return saveOrUpdateTwitterUser(userEntity);
        }

        return userEntity;
    }

    private UserEntity saveOrUpdateFoursquareUser(UserEntity userEntity) {

        UserEntity u = this.getUserByFoursquareId(userEntity.getFoursquareId());

        if( u == null ) {

            if( userEntity.getEmail() != null ) {
                u = this.getUserByEmail(userEntity.getEmail());

                if( u == null )
                    return saveOrUpdateByFoursquareTwitterUsername(userEntity);
                else {
                    //found user with the same email. need to update only foursquare fields!
                    Update update = new Update()
                            .set("foursquareId", userEntity.getFoursquareId())
                            .set("foursquareToken", userEntity.getFoursquareToken() )
                            .set("foursquareTwitterUsername", userEntity.getFoursquareTwitterUsername());

                    return findAndUpdate(userEntity, update, "email");
                }
            }
            else
                return saveOrUpdateByFoursquareTwitterUsername(userEntity);
        }
        else {
            //found saved foursquare user. need to update
            Update update = new Update()
                    .set("firstName", userEntity.getFirstName())
                    .set("lastName", userEntity.getLastName())
                    .set("foursquareToken", userEntity.getFoursquareToken() )
                    .set("foursquareTwitterUsername", userEntity.getFoursquareTwitterUsername())
                    .set("email", userEntity.getEmail());

            return findAndUpdate(userEntity, update, "foursquareId");
        }
    }

    private UserEntity saveOrUpdateByFoursquareTwitterUsername(UserEntity userEntity) {
        String foursquareTwitterUsername = userEntity.getFoursquareTwitterUsername();

        if( foursquareTwitterUsername != null ) {
            UserEntity userByTwitterUsername = this.getUserByTwitterUsername(foursquareTwitterUsername);

            if( userByTwitterUsername == null )
                return justSave(userEntity); //did not found fs user and our email is null - cannot find this user in DB just save
            else {
                Update update = new Update()
                        .set("foursquareId", userEntity.getFoursquareId())
                        .set("foursquareToken", userEntity.getFoursquareToken() )
                        .set("foursquareTwitterUsername", userEntity.getFoursquareTwitterUsername())
                        .set("email", userEntity.getEmail());

                return findAndUpdate(userByTwitterUsername, update, "twitterUsername");
            }
        }
        else {
            return justSave(userEntity); //did not found fs user and our email is null - cannot find this user in DB just save
        }
    }

    private UserEntity saveOrUpdateFacebookUser(UserEntity userEntity) {

        UserEntity u = this.getUserByFacebookId(userEntity.getFacebookId());

        if( u == null ) {
            if( userEntity.getEmail() != null ) {
                u = this.getUserByEmail(userEntity.getEmail());

                if( u == null )
                    return justSave(userEntity);
                else {
                    //found user with the same email. need to update only facebook fields!
                    Update update = new Update()
                            .set("facebookId", userEntity.getFacebookId())
                            .set("facebookToken", userEntity.getFacebookToken() );

                    return findAndUpdate(userEntity, update, "email");
                }
            }
            else
                return justSave(userEntity);
        }
        else {
            //found saved facebook user. need to update
            Update update = new Update()
                    .set("firstName", userEntity.getFirstName())
                    .set("lastName", userEntity.getLastName())
                    .set("facebookToken", userEntity.getFacebookToken() )
                    .set("email", userEntity.getEmail());

            return findAndUpdate(userEntity, update, "facebookId");
        }
    }

    private UserEntity saveOrUpdateTwitterUser(UserEntity userEntity) {

        UserEntity u = this.getUserByTwitterId(userEntity.getTwitterId());

        if( u == null ) {
            if( userEntity.getEmail() != null ) {
                u = this.getUserByEmail(userEntity.getEmail());

                if( u == null )
                    return saveOrUpdateByTwitterUsername(userEntity);
                else {
                    //found user with the same email. need to update only twitter fields!
                    Update update = new Update()
                            .set("twitterId", userEntity.getTwitterId())
                            .set("twitterToken", userEntity.getTwitterToken() )
                            .set("twitterUsername", userEntity.getTwitterUsername() );

                    return findAndUpdate(userEntity, update, "email");
                }
            }
            else
                return saveOrUpdateByTwitterUsername(userEntity);
        }
        else {
            //found saved facebook user. need to update
            Update update = new Update()
                    .set("firstName", userEntity.getFirstName())
                    .set("lastName", userEntity.getLastName())
                    .set("twitterToken", userEntity.getTwitterToken() )
                    .set("twitterUsername", userEntity.getTwitterUsername() )
                    .set("email", userEntity.getEmail());

            return findAndUpdate(userEntity, update, "twitterId");
        }
    }

    private UserEntity saveOrUpdateByTwitterUsername(UserEntity userEntity) {
        String twitterUsername = userEntity.getTwitterUsername();

        if( twitterUsername != null ) {
            UserEntity entity = this.getUserByFoursquareTwitterUsername(twitterUsername);

            if( entity == null )
                return justSave(userEntity);
            else {
                Update update = new Update()
                        .set("twitterId", userEntity.getTwitterId())
                        .set("twitterToken", userEntity.getTwitterToken() )
                        .set("twitterUsername", userEntity.getTwitterUsername() );

                return findAndUpdate(entity, update, "foursquareTwitterUsername");
            }
        }
        else {
            return justSave(userEntity);
        }
    }

    private UserEntity justSave(UserEntity userEntity) {
        op.save(userEntity);
        return userEntity;
    }

    private UserEntity findAndUpdate(UserEntity userEntity, Update update, String field) {

        BeanWrapper wrapper = new BeanWrapperImpl(userEntity);
        String value = (String) wrapper.getPropertyValue(field);

        Query query = new Query(Criteria.where(field).is(value));
        UserEntity u = op.findAndModify(query, update, new FindAndModifyOptions().returnNew(true), UserEntity.class);
        return u;
    }
}