package com.simple.neteasesimple.until;

import android.content.Context;
import android.support.annotation.NonNull;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.AppGlideModule;

@GlideModule
public final class BaseGlideAppModule extends AppGlideModule {
    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        int memoryCacheSizeBytes = 1024 * 1024 * 20;
        int diskCacheSizeBytes = 1024 * 1024 *100;
        builder.setMemoryCache(new LruResourceCache(memoryCacheSizeBytes))
                .setDiskCache(new InternalCacheDiskCacheFactory(context, diskCacheSizeBytes));
    }
}
