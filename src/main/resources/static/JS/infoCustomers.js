$(document).ready(function() {
    $(".infoCustomers").click(function(){
      var divCustomer = $(this)[0];
      var divInfoLeadAboutCustomer = divCustomer.children[2];

      var userNameSending = divInfoLeadAboutCustomer.children[0].innerHTML;
      var annualRevenueSending = divInfoLeadAboutCustomer.children[1].innerHTML;
      var streetSending = divInfoLeadAboutCustomer.children[2].innerHTML;
      var postalCodeSending = divInfoLeadAboutCustomer.children[3].innerHTML;
      var citySending = divInfoLeadAboutCustomer.children[4].innerHTML;
      var countrySending = divInfoLeadAboutCustomer.children[5].innerHTML;
      var creationDateSending = divInfoLeadAboutCustomer.children[6].innerHTML;
      var geographicPointSending = divInfoLeadAboutCustomer.children[7].innerHTML;
      var stateSending = divInfoLeadAboutCustomer.children[8].innerHTML;
      var phoneSending = divInfoLeadAboutCustomer.children[9].innerHTML;

      writeCustomerNotice(userNameSending, annualRevenueSending, streetSending, postalCodeSending, citySending, countrySending, creationDateSending.substring(0, 10), geographicPointSending, stateSending, phoneSending);
    });

    $('#boutonData').change(function() {
        if(this.checked) {
            $("#dataXml").hide();
            $("#dataText").show();
            $('#typeData').text("TEXT");
        } else {
            $("#dataXml").show();
            $("#dataText").hide();
            $('#typeData').text("XML");
        }
    });
});

function writeCustomerNotice(userNameSending, annualRevenueSending, streetSending, postalCodeSending, citySending, countrySending, creationDateSending, geographicPointSending, stateSending, phoneSending) {
      $("#userName").text(userNameSending);
      $("#annualRevenue").text(annualRevenueSending);
      $("#street").text(streetSending);
      $("#postalCode").text(postalCodeSending);
      $("#city").text(citySending);
      $("#country").text(countrySending);
      $("#creationDate").text(creationDateSending);
      $("#geographicPoint").text(geographicPointSending);
      $("#state").text(stateSending);
      $("#phone").text(phoneSending);
}