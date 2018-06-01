package com.boe.dacrestapi.model;

import java.util.Arrays;

import org.springframework.web.multipart.MultipartFile;

public class UploadModel {
	private String extraField;
	private MultipartFile[] files;
	private String paintingName;
	private String paintingDes;
	private String paintingType;
	public String getExtraField() {
		return extraField;
	}
	public void setExtraField(String extraField) {
		this.extraField = extraField;
	}
	public MultipartFile[] getFiles() {
		return files;
	}
	public void setFiles(MultipartFile[] files) {
		this.files = files;
	}
	public String getPaintingName() {
		return paintingName;
	}
	public void setPaintingName(String paintingName) {
		this.paintingName = paintingName;
	}
	public String getPaintingDes() {
		return paintingDes;
	}
	public void setPaintingDes(String paintingDes) {
		this.paintingDes = paintingDes;
	}
	public String getPaintingType() {
		return paintingType;
	}
	public void setPaintingType(String paintingType) {
		this.paintingType = paintingType;
	}
	@Override
    public String toString() {
		return "UploadModel{" +
                "extraField=\"" + extraField + '\"' +
                ",paintingName=\"" + paintingName + '\"' +
                ",paintingDes=\"" + paintingDes + '\"' +
                ",paintingType=\"" + paintingType + '\"' +
                ",files=\"" + Arrays.toString(files) +
                "\"}";
	}
}
