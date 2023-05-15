function handleSubmit(xhr, status, args, dialog) {
    var jqDialog = jQuery('#' + dialog.id);
    if (args.validationFailed) {
        //jqDialog.effect('shake', {times: 3}, 100);
    } else {
        dialog.hide();
    }
}

function selectTypeCount(thisObject) {
    var thisObjectVal = parseInt(thisObject.value === "" ? "0" : thisObject.value);
    if (thisObjectVal === parseInt(thisObjectVal, 10)) {
        thisObject.value = parseInt(thisObjectVal);
        var thisObjectId = thisObject.id;
        var costCustId = thisObjectId.substring(0, thisObjectId.lastIndexOf(":")) + ":ListIoTypesTitle_typeDefaultCostForCust";
        var costCustValue = parseFloat("0");
        if (document.getElementById(costCustId) !== null) {
            costCustValue = parseFloat(document.getElementById(costCustId).innerHTML === "" ? "0" : document.getElementById(costCustId).innerHTML);
        }

        var costUnitId = thisObjectId.substring(0, thisObjectId.lastIndexOf(":")) + ":ListIoTypesTitle_typeDefaultCostForUnit";
        var costUnitValue = parseFloat("0");
        if (document.getElementById(costUnitId) !== null) {
            costUnitValue = parseFloat(document.getElementById(costUnitId).innerHTML);
        }

        var totalCostForTypeId = thisObjectId.substring(0, thisObjectId.lastIndexOf(":")) + ":ListBillClosesTypesTitle_typeTotalCost";

        var totalCostForTypeValue = parseFloat(costCustValue) * parseInt(thisObjectVal);
        document.getElementById(totalCostForTypeId).innerHTML = totalCostForTypeValue.toFixed(2);

        //calculateSumAndTotal();
    } else {
        alert("عدد الوحدات يجب أن يكون رقم صحيح");
    }
}

function calculateSumAndTotal() {
    var billSumObject = document.getElementsByName("BillsCreateForm:billSum")[0];
    //load on all sub summations and calculate total and sum
    var firstTable = document.getElementById("BillsCreateForm:typesDatalistFirstHalf").childNodes[1].childNodes[0];
    var fistTableLength = firstTable.rows.length;
    var tempSum = 0;

    // Calculate summation value for first half
    for (var rowIndex = 0; rowIndex < fistTableLength - 1; rowIndex++) {
        var totalCostRow = document.getElementById("BillsCreateForm:typesDatalistFirstHalf:" + rowIndex + ":ListBillClosesTypesTitle_typeTotalCost");
        var totalCostForTypeValueRow = parseFloat(totalCostRow.innerHTML === "" ? "0" : totalCostRow.innerHTML);
        tempSum = parseFloat(tempSum) + parseFloat(totalCostForTypeValueRow);
    }

    //load on all sub summations and calculate total and sum
    var lastTable = document.getElementById("BillsCreateForm:typesDatalistLastHalf").childNodes[1].childNodes[0];
    var lastTableLength = lastTable.rows.length;

    // Calculate summation value for last half
    for (var rowIndex = 0; rowIndex < lastTableLength - 1; rowIndex++) {
        var totalCostRow = document.getElementById("BillsCreateForm:typesDatalistLastHalf:" + rowIndex + ":ListBillClosesTypesTitle_typeTotalCost");
        var totalCostForTypeValueRow = parseFloat(totalCostRow.innerHTML === "" ? "0" : totalCostRow.innerHTML);
        tempSum = parseFloat(tempSum) + parseFloat(totalCostForTypeValueRow);
    }
    billSumObject.value = parseFloat(tempSum);

    // Get Discount Value
    var billDiscountObject = document.getElementsByName("BillsCreateForm:billDiscount")[0];
    var billDiscountValue = parseFloat(billDiscountObject.value === "" ? "0" : billDiscountObject.value);
    if (billDiscountValue === "") {
        billDiscountObject.value === "0";
    }
    var billTotalObject = document.getElementsByName("BillsCreateForm:billTotal")[0];

    // Set Total value
    var totalResult = parseFloat(billSumObject.value) - parseFloat(billDiscountValue);
    if (parseFloat(totalResult) <= 0) {
        billTotalObject.style.color = "red";
    } else {
        billTotalObject.style.color = "black";
    }
    billTotalObject.value = parseFloat(totalResult);
}

function fixPFMDialogs() {
    jQuery("body > div[data-role='page']").each(function (i) {
        var pageId = jQuery(this).attr("id");
        jQuery("body > div[id*='" + pageId + "'][class*='ui-popup']").appendTo("#" + pageId);
    });
}

function hideSystemCols() {
    var tbl = document.getElementById("advancedSearchForm:docLst").childNodes[1].childNodes[0];

    for (var i = 0; i < tbl.rows.length; i++) {
        for (var j = 0; j < tbl.rows[i].cells.length; j++) {
            tbl.rows[i].cells[j].style.display = "";
            if (j === 0 || j === 1 || j === 2) {
                tbl.rows[i].cells[j].style.display = "none";
            }
        }
    }
    
}