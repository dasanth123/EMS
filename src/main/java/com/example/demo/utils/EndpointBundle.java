package com.example.demo.utils;

public class EndpointBundle {
    public static final  String BASE_URL="api/v1";
    public static final String EMPLOYEE=BASE_URL+"/employee";
    public static final  String EMPLOYEE_CREATE="/added";
    public static final String ID="/{id}";
    public  static  final  String BY_ID=EMPLOYEE+ID;


    public  static  final  String USER=BASE_URL+"/users";
    public  static  final  String USER_BY_ID=USER+ID;

    public  static  final  String SETTING=BASE_URL+"/setting";
    public  static  final  String DEPARTMENT=SETTING+"/department";



}
