package com.tos.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.SimpleWebServer;
import fi.iki.elonen.WebServerPluginInfo;
import fi.iki.elonen.util.ServerRunner;

public class VideoStreamServer extends SimpleWebServer {

    long fileLength;
    String fileName;

    public VideoStreamServer(int port, String file, long fileLength) {
        super(null, port, new File(file), false);
        this.fileLength = fileLength;
        this.fileName = file;
    }

    public static void main(int port, String files) {


        String host = null; // bind to all interfaces by default
        List<File> rootDirs = new ArrayList<File>();
        boolean quiet = false;
        String cors = null;
        Map<String, String> options = new HashMap<String, String>();

        if (rootDirs.isEmpty()) {
            rootDirs.add(new File(files).getAbsoluteFile());
        }
        options.put("host", host);
        options.put("port", "" + port);
        options.put("quiet", String.valueOf(quiet));
        StringBuilder sb = new StringBuilder();
        for (File dir : rootDirs) {
            if (sb.length() > 0) {
                sb.append(":");
            }
            try {
                sb.append(dir.getCanonicalPath());
            } catch (IOException ignored) {
            }
        }
        options.put("home", sb.toString());
        System.out.println(sb.toString()+"dddddd");

        ServiceLoader<WebServerPluginInfo> serviceLoader = ServiceLoader.load(WebServerPluginInfo.class);
        for (WebServerPluginInfo info : serviceLoader) {
            String[] mimeTypes = info.getMimeTypes();
            for (String mime : mimeTypes) {
                String[] indexFiles = info.getIndexFilesForMimeType(mime);
                if (!quiet) {
                    System.out.print("# Found plugin for Mime type: \"" + mime + "\"");
                    if (indexFiles != null) {
                        System.out.print(" (serving index files: ");
                        for (String indexFile : indexFiles) {
                            System.out.print(indexFile + " ");
                        }
                    }
                    System.out.println(").");
                }
                registerPluginForMimeType(indexFiles, mime, info.getWebServerPlugin(mime), options);
            }
        }

        try {
            SimpleWebServer p =  new SimpleWebServer(host, port, rootDirs, quiet, cors);
                    p.start(SOCKET_READ_TIMEOUT, false);
            System.out.println("server start !!!!!");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
