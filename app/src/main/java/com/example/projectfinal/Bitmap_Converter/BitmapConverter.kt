package com.example.projectfinal.Bitmap_Converter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import com.example.projectfinal.R
import java.io.*
import java.util.*

class BitmapConverter {

        fun bitmapToString(image: Bitmap): String {
            var baos = ByteArrayOutputStream()
            image.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            var b = baos.toByteArray()
            var temp: String? = null
            try {
                System.gc()
                temp = Base64.encodeToString(b, Base64.DEFAULT)
            } catch (e: Exception) {
                e.printStackTrace()
            } catch (e: OutOfMemoryError) {
                baos = ByteArrayOutputStream()
                image.compress(Bitmap.CompressFormat.JPEG, 50, baos)
                b = baos.toByteArray()
                temp = Base64.encodeToString(b, Base64.DEFAULT)
            }
            return temp ?: "null"
        }

        fun stringToBitmap(context: Context, input: String): Bitmap {
            val decodedByte = Base64.decode(input, 0)

            val sdCardDirectory = File(context.cacheDir, "")

            val rand = Random()

            // nextInt is normally exclusive of the top value,
            // so add 1 to make it inclusive
            val randomNum = rand.nextInt((1000 - 0) + 1) + 0

            val nw = "IMG_$randomNum.txt"
            val image = File(sdCardDirectory, nw)

            // Encode the file as a PNG image.
            var outStream: FileOutputStream?
            try {
                outStream = FileOutputStream(image)
                outStream.write(input.toByteArray())
                outStream.flush()
                outStream.close()
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return try {
                BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.size)
            } catch (e: OutOfMemoryError) {
                e.printStackTrace()
                val iss = context.resources.openRawResource(R.raw.placeholder)
                BitmapFactory.decodeStream(iss)
            } catch (e: Exception) {
                e.printStackTrace()
                val iss = context.resources.openRawResource(R.raw.placeholder)
                BitmapFactory.decodeStream(iss)
            }
        }
    }
