package com.example.login_verification

//data class User(var id:String?=null,var name:String?=null,var username:String?=null,var password:String?=null)
class User{
    var name:String?=null
    var username:String?=null
    var id:String?=null


    constructor(){}
    constructor(name:String?, email:String?, id:String?){
        this.name=name
        this.username=username
        this.id=id
    }
}


