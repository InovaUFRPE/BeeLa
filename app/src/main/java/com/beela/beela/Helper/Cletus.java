package com.beela.beela.Helper;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Cletus {


        Map<String, Integer> base;

        public Cletus(Map<String, Integer> base) {
            this.base = base;
        }

        // Note: this comparator imposes orderings that are inconsistent with
        // equals.
        public int compare(String a, String b) {
            if (base.get(a) >= base.get(b)) {
                return -1;
            } else {
                return 1;
            } // returning 0 would merge keys
        }




}
