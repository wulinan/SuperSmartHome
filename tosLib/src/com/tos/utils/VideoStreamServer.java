package com.tos.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Map;

import fi.iki.elonen.NanoHTTPD;

public class VideoStreamServer extends NanoHTTPD
{

    long fileLength;
    String fileName;
    public VideoStreamServer(int port, String file, long fileLength) {
        super(port);
        this.fileLength = fileLength;
        this.fileName = file;
    }

    private Response getPartialResponse(String mimeType, String rangeHeader) throws IOException {
        File file = new File(this.fileName);
        String rangeValue = rangeHeader.trim().substring("bytes=".length());
        long fileLength = file.length();
        long start, end;
        if (rangeValue.startsWith("-")) {
            end = fileLength - 1;
            start = fileLength - 1
                    - Long.parseLong(rangeValue.substring("-".length()));
        } else {
            String[] range = rangeValue.split("-");
            start = Long.parseLong(range[0]);
            end = range.length > 1 ? Long.parseLong(range[1])
                    : fileLength - 1;
        }
        if (end > fileLength - 1) {
            end = fileLength - 1;
        }
        if (start <= end) {
            long contentLength = end - start + 1;
            //cleanupStreams();
            FileInputStream fileInputStream = new FileInputStream(file);
            //noinspection ResultOfMethodCallIgnored
            fileInputStream.skip(start);
            Response response = NanoHTTPD.newFixedLengthResponse(Response.Status.PARTIAL_CONTENT, mimeType, fileInputStream,contentLength);
            response.addHeader("Content-Length", contentLength + "");
            response.addHeader("Content-Range", "bytes " + start + "-" + end + "/" + fileLength);
            System.out.println("SERVER_PARTIAL"+"bytes " + start + "-" + end + "/" + fileLength);
            response.addHeader("Content-Type", mimeType);
            return response;
        } else {
            return NanoHTTPD.newFixedLengthResponse(Response.Status.RANGE_NOT_SATISFIABLE, "video/mp4", rangeHeader);
        }
    }

    @Override
    public Response serve(String uri, Method method, Map<String, String> headers, Map<String, String> parms, Map<String, String> files) {

        //range=bytes=619814-
        long range;
        int constantLength = 307200 ;
        long fileLength=0;
        boolean isLastPart=false;
        String rangeHeaderString="";
        if (headers.containsKey("range"))
        {
            String contentRange = headers.get("range");
            range = Integer.parseInt(contentRange.substring(contentRange.indexOf("=") + 1, contentRange.indexOf("-")));

        }
        else
        {
            range = 0;

        }



        byte[] buffer;


        long bufLength=0;


        try {

            RandomAccessFile ff =new RandomAccessFile(new File(this.fileName),"rw" );
            long remainingChunk = ff.length() - range; //remaining
            fileLength = ff.length();
            if (remainingChunk < constantLength){
                bufLength= remainingChunk; //means last part
                isLastPart=true;

            }

            else
                bufLength = constantLength;
            if (range !=0)
                ff.seek(range);
            buffer= new byte[(int)bufLength];


            ff.read(buffer);
            rangeHeaderString = String.format("bytes=%s-%s",range,range+bufLength);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
            buffer = new byte[0];
        } catch (IOException e) {
            e.printStackTrace();
            buffer = new byte[0];
        }
        Response response;
           if(!isLastPart)
        	   response = NanoHTTPD.newFixedLengthResponse(Response.Status.PARTIAL_CONTENT,"video/mp4",new ByteArrayInputStream(buffer),buffer.length);
           else
        	   response =NanoHTTPD.newFixedLengthResponse(Response.Status.OK,"video/mp4",new ByteArrayInputStream(buffer),buffer.length);

        response.addHeader("Content-Length",(bufLength)+"");
        response.addHeader("Content-Range",String.format("bytes %s-%s/%s", range,(range+bufLength),fileLength));
        System.out.println("SERVER "+"Inside server sent " + String.format("bytes %s-%s/%s", range, (range + bufLength), fileLength));
        return response;
//        try {
//            Response res =getPartialResponse("video/mp4",rangeHeaderString);
//            return res;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return new Response(Response.Status.NOT_FOUND,"","");

    }
}

