package com.aa.ticketing;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Trie {
/**
 * Prefix tree for path exploration
 * **/
    private Map<String, Trie> children;
    private Map<String, String> parameters;

    public Trie(){
        this.children = new HashMap<>();
        this.parameters = new HashMap<>();
    }
    /**
     * given a parameter for a template as user.address.street : streetName
     * this function will create a prefix tree for quick lookup of parameter over a tree structure
     * this can work hand in hand with the composite design pattern that is going to be used
     * for template rendering mechanism
     * **/
    public void addParam(String key, String value){
        Trie current  = this;
        List paramPaths = Arrays.asList(key.split("\\."));
        Iterator<String> iterator = paramPaths.iterator();
        String k = "";
        while(iterator.hasNext()){
            k = iterator.next();
            // we need t o make sure the last parameter is stored in the hashmap and is not used to create a Trie
            if(iterator.hasNext()) {
                if (!current.children.containsKey(k))
                    current.children.put(k, new Trie());
                current = current.children.get(k);
            }
        }
        current.parameters.put(k, value);
    }

    public Map<String, String> getParams(String key){//user.address.addres1
        Trie current  = this;
        List paramPaths = Arrays.asList(key.split("\\."));
        Iterator<String> iterator = paramPaths.iterator();
        String k = "";
        while(iterator.hasNext()){
            k = iterator.next();
            if(!current.children.containsKey(k))
                return null;
            current = current.children.get(k);
        }
        return current.parameters;
    }

    public Map<String, String> getParams(){//user.address.addres1
        return this.parameters;
    }

    public Trie getTrie(String key){//user.address.addres1
        Trie current  = this;
        List paramPaths = Arrays.asList(key.split("\\."));
        Iterator<String> iterator = paramPaths.iterator();
        String k = "";
        while(iterator.hasNext()){
            k = iterator.next();
            if(!current.children.containsKey(k))
                return null;
            current = current.children.get(k);
        }
        return current;
    }

}
