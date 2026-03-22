# ── Firebase Firestore ────────────────────────────────────────────────────────
# Keep all data model classes so Firestore's toObject() can deserialize them
-keep class com.tripbook.app.data.model.** { *; }
-keepattributes Signature
-keepattributes *Annotation*

# ── Firebase Auth ─────────────────────────────────────────────────────────────
-keepattributes EnclosingMethod

# ── Glide ─────────────────────────────────────────────────────────────────────
#-keep public class * implements com.bumptech.glide.module.GlideModule
#-keep class * extends com.bumptech.glide.module.AppGlideModule { <init>(...); }
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

# ── Google Maps ───────────────────────────────────────────────────────────────
-keep class com.google.android.gms.maps.** { *; }

# ── Navigation Safe Args (generated classes) ──────────────────────────────────
-keep class com.tripbook.app.ui.**.*Args { *; }
-keep class com.tripbook.app.ui.**.*Directions { *; }

# ── Kotlin ────────────────────────────────────────────────────────────────────
-keep class kotlin.** { *; }
-dontwarn kotlin.**
