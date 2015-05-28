/*
 * EasyCukes is just a framework aiming at making Cucumber even easier than what it already is.
 * Copyright (C) 2014 Worldline or third-party contributors as
 * indicated by the @author tags or express copyright attribution
 * statements applied by the authors.
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License.
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 */
package com.worldline.easycukes.commons.helpers;

import lombok.Cleanup;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * {@link FileHelper} allows to perform certain operations in relation to the
 * manipulation of file zip, which containing a bunch of files.
 *
 * @author mechikhi
 * @version 1.0
 */
@Slf4j
@UtilityClass
public class FileHelper {

    /**
     * Size of the buffer to read/write data
     */
    private static final int BUFFER_SIZE = 4096;

    /**
     * Downloads some content from an URL to a specific directory
     *
     * @param from a {@link String} representation of the URL on which the
     *             content should be downloaded
     * @param to   the path on which the content should be downloaded
     * @throws IOException if anything's going wrong while downloading the content to
     *                     the specified directory
     */
    public static void download(@NonNull String from, @NonNull String to) throws IOException {
        final URL url = new URL(from);
        log.debug("Downloading from: " + url.toString() + " to: "
                + to.toString());
        final String zipFilePath = to
                + url.getFile().substring(url.getFile().lastIndexOf('/'));
        FileUtils.copyURLToFile(url, new File(zipFilePath), 30000, 300000);
    }

    /**
     * Extracts the content of a zip folder in a specified directory
     *
     * @param from a {@link String} representation of the URL containing the zip
     *             file to be unzipped
     * @param to   the path on which the content should be extracted
     * @throws IOException if anything's going wrong while unzipping the content of the
     *                     provided zip folder
     */
    public static void unzip(@NonNull String from, @NonNull String to, boolean isRemote)
            throws IOException {
        @Cleanup ZipInputStream zip = null;
        if (isRemote)
            zip = new ZipInputStream(new FileInputStream(from));
        else
            zip = new ZipInputStream(FileHelper.class.getResourceAsStream(from));
        log.debug("Extracting zip from: " + from + " to: " + to);
        // Extract without a container directory if exists
        ZipEntry entry = zip.getNextEntry();
        String rootDir = "/";
        if (entry != null)
            if (entry.isDirectory())
                rootDir = entry.getName();
            else {
                final String filePath = to + entry.getName();
                // if the entry is a file, extracts it
                try {
                    extractFile(zip, filePath);
                } catch (final FileNotFoundException fnfe) {
                    log.warn(fnfe.getMessage(), fnfe);
                }
            }
        zip.closeEntry();
        entry = zip.getNextEntry();
        // iterates over entries in the zip file
        while (entry != null) {
            String entryName = entry.getName();
            if (entryName.startsWith(rootDir))
                entryName = entryName.replaceFirst(rootDir, "");
            final String filePath = to + "/" + entryName;
            if (!entry.isDirectory())
                // if the entry is a file, extracts it
                try {
                    extractFile(zip, filePath);
                } catch (final FileNotFoundException fnfe) {
                    log.warn(fnfe.getMessage(), fnfe);
                }
            else {
                // if the entry is a directory, make the directory
                final File dir = new File(filePath);
                dir.mkdir();
            }
            zip.closeEntry();
            entry = zip.getNextEntry();
        }
        // delete the zip file if recovered from URL
        if (isRemote)
            new File(from).delete();
    }

    /**
     * Extracts a specific file from a zip folder
     *
     * @param zip      a {@link ZipInputStream} corresponding to the zip folder to be
     *                 extracted
     * @param filePath the path of the file to be extracted from the zip folder
     * @throws IOException if anything's going wrong while extracting the content of the
     *                     zip
     */
    private static void extractFile(@NonNull ZipInputStream zip, @NonNull String filePath)
            throws IOException {
        @Cleanup final BufferedOutputStream bos = new BufferedOutputStream(
                new FileOutputStream(filePath));
        final byte[] bytesIn = new byte[BUFFER_SIZE];
        int read = 0;
        while ((read = zip.read(bytesIn)) != -1)
            bos.write(bytesIn, 0, read);
    }

    /**
     * Deletes directory and its sub directories
     *
     * @param file the directory to be deleted
     * @return true if everything went fine, false otherwise.
     */
    public static boolean deleteDirectory(@NonNull File file) {
        if (file.exists()) {
            final File[] children = file.listFiles();
            if (children != null) {
                for (final File child : children)
                    if (child.isDirectory())
                        deleteDirectory(child);
                    else
                        child.delete();
                return file.delete();
            }
        }
        return true;
    }
}
