// IBookManager.aidl
package com.example.artaidlserver;

// Declare any non-default types here with import statements

import com.example.artaidlserver.Book;
import com.example.artaidlserver.IOnNewBookArrivedListener;

interface IBookManager {
     List<Book> getBookList();
     void addBook(in Book book);
     void registerListener(IOnNewBookArrivedListener listener);
     void unregisterListener(IOnNewBookArrivedListener listener);
}