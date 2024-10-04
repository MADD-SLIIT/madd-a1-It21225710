package com.example.myapplication.data_class

class Guide{
    var uid: String ?= null
    var name: String ?= null
    var email: String ?= null
    var password:String ?= null
    var phone:String ?= null
    var imageUrl: String ?= null

    constructor()

    constructor(name:String,email: String,uid: String,password:String,phone:String,ImageUri:String){

        this.name = name
        this.email = email
        this.uid = uid
        this.password = password
        this.phone = phone
        this.imageUrl = ImageUri
    }
}
