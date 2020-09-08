package com.uxcraft.mvvmsampleproject.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


const val CURRENT_USER_ID = 0

@Entity
data class User(


//map this user as received from the backend ******THIS IS THE RESPONSE FROM

//    var id:Int?=null,
//    var name:String?=null,
//    var email:String?=null,
//    var email_verfified_at:String?=null,
//    var created_at:String?= null,
//    var updated_at:String?=null

    var id: String?=null,
var email: String?=null,
var provider: String?=null,
var countries_id: String?=null,
var date_of_birth: String?=null,
var  first_name: String?=null,
var last_name: String?=null,
var phone_number: String?=null,
var id_number: String?=null,
var uid: String?=null,
var allow_password_change: Boolean?= true,
var name :String?=null,
var nickname: String?=null,
var image: String?=null,
var marital_status: String?=null,
var gender: String?=null,
var employed: Boolean?=true,
var musoni_id: Int?=0,
var customer_standing: String?=null,
var status: Int?=0,
var deleted_at: String?=null,
var cold_call: Boolean?= true,
var referral_source: String?=null,
var assignee: String?=null



    ) {
/*
WE ONLY WANT TO STORE ONE USER IN ROOM AS WE CANT HAVE TWO AUTHENTICATED USERS IN AN APP
PRIMARY KEY WILL BE THE CURRENT USER ID, INCASE OF NEW ID WE OVERWRITE CURRENT, CASE OF SAME ID ALSO OVERWRITE EXISTING
AUTOGENERATE IS FALSE AS WE DONT WANT KEY TO BE INCREMENTED SINCE WE CAN ONLY HAVE ONE USER
*/

    @PrimaryKey(autoGenerate = false)
    var uuid: Int = CURRENT_USER_ID


}