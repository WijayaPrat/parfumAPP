package com.wijayaprat.fragrancecenter.helper

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

class ImageUploadHelper {

    private val storageRef =
        FirebaseStorage.getInstance().reference.child("parfum_images")

    fun uploadImage(
        imageUri: Uri,
        onSuccess: (String) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val fileName = "${UUID.randomUUID()}.jpg"
        val imageRef = storageRef.child(fileName)

        imageRef.putFile(imageUri)
            .addOnSuccessListener {
                imageRef.downloadUrl
                    .addOnSuccessListener { uri ->
                        onSuccess(uri.toString())
                    }
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }
}
