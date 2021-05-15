package com.vivek.wmsapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Customer implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("customerName")
    @Expose
    private String customerName;
    @SerializedName("fatherOrHusbandName")
    @Expose
    private String fatherOrHusbandName;
    @SerializedName("caste")
    @Expose
    private String caste;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("houseNumber")
    @Expose
    private String houseNumber;
    @SerializedName("streetNumber")
    @Expose
    private String streetNumber;
    @SerializedName("propertyType")
    @Expose
    private String propertyType;
    @SerializedName("buildingCoveredArea")
    @Expose
    private String buildingCoveredArea;
    @SerializedName("landCoveredArea")
    @Expose
    private String landCoveredArea;
    @SerializedName("buildingCarpetArea")
    @Expose
    private String buildingCarpetArea;
    @SerializedName("landCarpetArea")
    @Expose
    private String landCarpetArea;
    @SerializedName("buildingUsedBy")
    @Expose
    private String buildingUsedBy;
    @SerializedName("yearOfConstruction")
    @Expose
    private String yearOfConstruction;
    @SerializedName("fixedMonthlyRateForBuilding")
    @Expose
    private String fixedMonthlyRateForBuilding;
    @SerializedName("fixedMonthlyRateForLand")
    @Expose
    private String fixedMonthlyRateForLand;
    @SerializedName("buildingAnnualValue")
    @Expose
    private String buildingAnnualValue;
    @SerializedName("landAnnualValue")
    @Expose
    private String landAnnualValue;
    @SerializedName("buildingAnnualValueAfterExemption")
    @Expose
    private String buildingAnnualValueAfterExemption;
    @SerializedName("totalAnnualValue")
    @Expose
    private String totalAnnualValue;
    @SerializedName("taxRate")
    @Expose
    private String taxRate;
    @SerializedName("taxOnAnnualValue")
    @Expose
    private String taxOnAnnualValue;
    @SerializedName("waterTax")
    @Expose
    private String waterTax;
    @SerializedName("place")
    @Expose
    private String place;
    @SerializedName("wardName")
    @Expose
    private String wardName;
    @SerializedName("wardNumber")
    @Expose
    private String wardNumber;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("subCategory")
    @Expose
    private String subCategory;
    @SerializedName("charges")
    @Expose
    private String charges;
    @SerializedName("serviceActivator")
    @Expose
    private String serviceActivator;
    @SerializedName("dateOfActivation")
    @Expose
    private String dateOfActivation;
    @SerializedName("dataCollector")
    @Expose
    private String dataCollector;
    @SerializedName("dueDate")
    @Expose
    private String dueDate;
    @SerializedName("otherDetails")
    @Expose
    private String otherDetails;
    @SerializedName("roadInFront")
    @Expose
    private String roadInFront;
    @SerializedName("waterConnection")
    @Expose
    private String waterConnection;

    public String getWaterConnection() {
        return waterConnection;
    }

    public void setWaterConnection(String waterConnection) {
        this.waterConnection = waterConnection;
    }

    public String getRoadInFront() {
        return roadInFront;
    }

    public void setRoadInFront(String roadInFront) {
        this.roadInFront = roadInFront;
    }

    public String getCaste() {
        return caste;
    }

    public void setCaste(String caste) {
        this.caste = caste;
    }

    public String getOtherDetails() {
        return otherDetails;
    }

    public void setOtherDetails(String otherDetails) {
        this.otherDetails = otherDetails;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getFatherOrHusbandName() {
        return fatherOrHusbandName;
    }

    public void setFatherOrHusbandName(String fatherOrHusbandName) {
        this.fatherOrHusbandName = fatherOrHusbandName;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public String getLandCoveredArea() {
        return landCoveredArea;
    }

    public void setLandCoveredArea(String landCoveredArea) {
        this.landCoveredArea = landCoveredArea;
    }

    public String getBuildingCarpetArea() {
        return buildingCarpetArea;
    }

    public void setBuildingCarpetArea(String buildingCarpetArea) {
        this.buildingCarpetArea = buildingCarpetArea;
    }

    public String getBuildingUsedBy() {
        return buildingUsedBy;
    }

    public void setBuildingUsedBy(String buildingUsedBy) {
        this.buildingUsedBy = buildingUsedBy;
    }

    public String getYearOfConstruction() {
        return yearOfConstruction;
    }

    public void setYearOfConstruction(String yearOfConstruction) {
        this.yearOfConstruction = yearOfConstruction;
    }

    public String getFixedMonthlyRateForBuilding() {
        return fixedMonthlyRateForBuilding;
    }

    public void setFixedMonthlyRateForBuilding(String fixedMonthlyRateForBuilding) {
        this.fixedMonthlyRateForBuilding = fixedMonthlyRateForBuilding;
    }

    public String getBuildingAnnualValue() {
        return buildingAnnualValue;
    }

    public void setBuildingAnnualValue(String buildingAnnualValue) {
        this.buildingAnnualValue = buildingAnnualValue;
    }

    public String getBuildingAnnualValueAfterExemption() {
        return buildingAnnualValueAfterExemption;
    }

    public void setBuildingAnnualValueAfterExemption(String buildingAnnualValueAfterExemption) {
        this.buildingAnnualValueAfterExemption = buildingAnnualValueAfterExemption;
    }

    public String getBuildingCoveredArea() {
        return buildingCoveredArea;
    }

    public void setBuildingCoveredArea(String buildingCoveredArea) {
        this.buildingCoveredArea = buildingCoveredArea;
    }

    public String getLandCarpetArea() {
        return landCarpetArea;
    }

    public void setLandCarpetArea(String landCarpetArea) {
        this.landCarpetArea = landCarpetArea;
    }

    public String getFixedMonthlyRateForLand() {
        return fixedMonthlyRateForLand;
    }

    public void setFixedMonthlyRateForLand(String fixedMonthlyRateForLand) {
        this.fixedMonthlyRateForLand = fixedMonthlyRateForLand;
    }

    public String getLandAnnualValue() {
        return landAnnualValue;
    }

    public void setLandAnnualValue(String landAnnualValue) {
        this.landAnnualValue = landAnnualValue;
    }

    public String getTotalAnnualValue() {
        return totalAnnualValue;
    }

    public void setTotalAnnualValue(String totalAnnualValue) {
        this.totalAnnualValue = totalAnnualValue;
    }

    public String getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(String taxRate) {
        this.taxRate = taxRate;
    }

    public String getTaxOnAnnualValue() {
        return taxOnAnnualValue;
    }

    public void setTaxOnAnnualValue(String taxOnAnnualValue) {
        this.taxOnAnnualValue = taxOnAnnualValue;
    }

    public String getWaterTax() {
        return waterTax;
    }

    public void setWaterTax(String waterTax) {
        this.waterTax = waterTax;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getWardName() {
        return wardName;
    }

    public void setWardName(String wardName) {
        this.wardName = wardName;
    }

    public String getWardNumber() {
        return wardNumber;
    }

    public void setWardNumber(String wardNumber) {
        this.wardNumber = wardNumber;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getCharges() {
        return charges;
    }

    public void setCharges(String charges) {
        this.charges = charges;
    }

    public String getServiceActivator() {
        return serviceActivator;
    }

    public void setServiceActivator(String serviceActivator) {
        this.serviceActivator = serviceActivator;
    }

    public String getDateOfActivation() {
        return dateOfActivation;
    }

    public void setDateOfActivation(String dateOfActivation) {
        this.dateOfActivation = dateOfActivation;
    }

    public String getDataCollector() {
        return dataCollector;
    }

    public void setDataCollector(String dataCollector) {
        this.dataCollector = dataCollector;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", customerName='" + customerName + '\'' +
                ", fatherOrHusbandName='" + fatherOrHusbandName + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", houseNumber='" + houseNumber + '\'' +
                ", streetNumber='" + streetNumber + '\'' +
                ", propertyType='" + propertyType + '\'' +
                ", buildingCoveredArea='" + buildingCoveredArea + '\'' +
                ", landCoveredArea='" + landCoveredArea + '\'' +
                ", buildingCarpetArea='" + buildingCarpetArea + '\'' +
                ", landCarpetArea='" + landCarpetArea + '\'' +
                ", buildingUsedBy='" + buildingUsedBy + '\'' +
                ", yearOfConstruction='" + yearOfConstruction + '\'' +
                ", fixedMonthlyRateForBuilding='" + fixedMonthlyRateForBuilding + '\'' +
                ", fixedMonthlyRateForLand='" + fixedMonthlyRateForLand + '\'' +
                ", buildingAnnualValue='" + buildingAnnualValue + '\'' +
                ", landAnnualValue='" + landAnnualValue + '\'' +
                ", buildingAnnualValueAfterExemption='" + buildingAnnualValueAfterExemption + '\'' +
                ", totalAnnualValue='" + totalAnnualValue + '\'' +
                ", taxRate='" + taxRate + '\'' +
                ", taxOnAnnualValue='" + taxOnAnnualValue + '\'' +
                ", waterTax='" + waterTax + '\'' +
                ", place='" + place + '\'' +
                ", wardName='" + wardName + '\'' +
                ", wardNumber='" + wardNumber + '\'' +
                ", category='" + category + '\'' +
                ", subCategory='" + subCategory + '\'' +
                ", charges='" + charges + '\'' +
                ", serviceActivator='" + serviceActivator + '\'' +
                ", dateOfActivation='" + dateOfActivation + '\'' +
                ", dataCollector='" + dataCollector + '\'' +
                ", dueDate='" + dueDate + '\'' +
                ", otherDetails='" + otherDetails + '\'' +
                '}';
    }
}
