package com.invoice2go.rishi.mycontacts.Model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by rishi on 1/14/2018.
 */

@Dao
public interface ContactDao {

    @Query("SELECT * FROM contacts ORDER BY first_name")
    List<Contact> getAll();

    @Query("SELECT * FROM contacts WHERE first_name LIKE :firstName LIMIT 1")
    Contact findByName(String firstName);

    /*@Query("SELECT * FROM contacts WHERE first_name LIKE :firstName ORDER BY first_name")
    List<Contact>  findAllByFirst(String firstName);

    @Query("SELECT * FROM contacts WHERE last_name LIKE :lastName ORDER BY last_name")
    List<Contact>  findAllByLast(String lastName);*/

    @Query("SELECT * FROM contacts WHERE first_name LIKE '%'|| :name || '%' OR last_name LIKE '%'|| :name || '%'")
    List<Contact>  findAll(String name);


    @Insert
    void insert(Contact contact);

    @Update
    void update(Contact contact);

    @Delete
    void delete(Contact contact);



}
