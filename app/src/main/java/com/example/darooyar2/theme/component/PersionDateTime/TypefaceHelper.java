package com.example.darooyar2.theme.component.PersionDateTime;


import android.content.Context;
import android.graphics.Typeface;

import java.util.HashMap;

public class TypefaceHelper {

  private static final HashMap<String, Typeface> cache = new HashMap<>();

  public static Typeface get(Context c, String name) {
    synchronized (cache) {
      if (!cache.containsKey(name)) {
        Typeface t = Typeface.createFromAsset(
          c.getAssets(), String.format("regular.ttf", name));
        cache.put(name, t);
        return t;
      }
      return cache.get(name);
    }
  }
}
