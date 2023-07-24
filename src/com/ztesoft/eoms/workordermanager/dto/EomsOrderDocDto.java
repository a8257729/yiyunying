package com.ztesoft.eoms.workordermanager.dto;

import java.io.*;

public class EomsOrderDocDto
    implements Serializable {
    private Long id;
    private String fileName;
    private byte[] documentContent;
    private String absoluteFilePath;
    private InputStream documentContentStream;
    private int fileLength;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getDocumentContent() {
        return documentContent;
    }

    public void setDocumentContent(byte[] documentContent) {
        this.documentContent = documentContent;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (! (obj instanceof EomsOrderDocDto)) {
            return false;
        }
        EomsOrderDocDto that = (EomsOrderDocDto) obj;
        if (! (that.id == null ? this.id == null :
               that.id.equals(this.id))) {
            return false;
        }
        if (! (that.fileName == null ? this.fileName == null :
               that.fileName.equals(this.fileName))) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        int result = 17;
        result = 37 * result + this.id.hashCode();
        result = 37 * result + this.fileName.hashCode();

        return result;
    }

    public String toString() {
        String returnString = "";
        returnString += id;
        returnString += ", " + fileName;

        return returnString;
    }

    public String getAbsoluteFilePath() {
        return absoluteFilePath;
    }

    public void setAbsoluteFilePath(String absoluteFilePath) {
        this.absoluteFilePath = absoluteFilePath;
    }
  public InputStream getDocumentContentStream() {
    return documentContentStream;
  }
  public void setDocumentContentStream(InputStream documentContentStream) {
    this.documentContentStream = documentContentStream;
  }
  public int getFileLength() {
    return fileLength;
  }
  public void setFileLength(int fileLength) {
    this.fileLength = fileLength;
  }

}

