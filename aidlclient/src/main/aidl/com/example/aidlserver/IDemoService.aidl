// IDemoService.aidl
package com.example.aidlserver;

// Declare any non-default types here with import statements

interface IDemoService {
    void setName(String name);
    String getName();
}