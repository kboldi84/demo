$(document).ready(function () {
let afakulcsok = [];

        $("#tetelek tbody select[id^='afakulcs1']> option ").each(function () {
            afakulcsok.push($(this).text());
        });
    



    $("body").delegate("a[name^='plusTetel']", "click", function () {


		var sorszam = $(this).attr("tetelbutton-id");
        $(this).addClass('disabled');
        var ujindex = parseInt(sorszam) + 1;

		var newTableRow = "<tr id='" + ujindex + "'>" +
            "<td><input type='text' name='megnevezes' class='form-control form-control-sm' data-megnevezes-id='"+ ujindex +"' id='megnevezes" + ujindex + "'   /></td>"+
            "<td><input type='text' name='mennyiseg' class='form-control form-control-sm' data-mennyiseg-id='"+ ujindex +"' id='mennyiseg" + ujindex + "'   /></td>"+
            "<td><input type='text' name='mennyisegegyseg' class='form-control form-control-sm' data-mennyisegegyseg-id='"+ ujindex +"' id='mennyisegegyseg" + ujindex + "'   /></td>"+
            "<td><input type='text' name='egysegar' class='form-control form-control-sm' data-egysegar-id='"+ ujindex +"' id='egysegar" + ujindex + "'   /></td>"+
            "<td><input type='text' name='netto' class='form-control form-control-sm' data-netto-id='"+ ujindex +"' id='netto" + ujindex + "'   /></td>"+
            "<td>"+
            "<select id='afakulcs" + ujindex + "'  data-afakulcs-id='"+ ujindex +"' name='afakulcs' class='form-select form-select-sm' aria-label='.form-select-sm example'>";
            afakulcsok.map( (kulcs) => {
                kulcs=='-' ? newTableRow = newTableRow +  "<option  selected value='" + kulcs + "' >" + kulcs + "</option>": newTableRow = newTableRow +  "<option  value='" + kulcs + "' >" + kulcs + "</option>";
                ;
            
            });

            newTableRow = newTableRow + "</select>"+
            "</td>"+
            "<td><input type='text' readonly name='afa' data-afa-id='"+ ujindex +"' id='afa" + ujindex + "' class='form-control form-control-sm' /></td>"+
            "<td><input type='text' readonly name='brutto' data-brutto-id='"+ ujindex +"' id='brutto" + ujindex + "' class='form-control form-control-sm' /></td>"+
            "<td>"+
            "<a name='plusTetel' class='btn btn-info btn-sm' tetelbutton-id='" + ujindex + "'>+</a>"+  
            "</td>"+
          "</tr>";



 		$("#tetelek tr:last").after(newTableRow);

	});

    // nettó számolása egységár és mennyiségből
    $("body").delegate("input[name^='egysegar']", "change", function () {
        let tetelId = $(this).attr("data-egysegar-id");
        let mennyiseg = $("#mennyiseg" + tetelId).val();
        let egysegar = $("#egysegar" + tetelId).val();
        
        let netto = parseInt(mennyiseg) * parseInt(egysegar) 
        $("#netto" + tetelId).val(netto);
         
      });


      // áfa mező változik
      $("body").delegate("#tetelek select[name^='afakulcs']", "change", function () {
                let tetelId = $(this).attr("data-afakulcs-id");
                let netto = $("#netto" + tetelId).val();
                let brutto = 0;

                let afakulcs = $("#afakulcs" + tetelId).val();

                if (afakulcs == 0){ brutto = netto; }
                if ((afakulcs == 5)|| (afakulcs == 10) || (afakulcs == 27)) { brutto = parseInt(netto) + (parseInt(netto) * parseInt(afakulcs))/100; }
                let afa = parseInt(brutto) - parseInt(netto);

                $("#afa" + tetelId).val(afa);
                $("#brutto" + tetelId).val(brutto);
        

      });

      
    $("body").delegate("#btnCreate", "click", function () {

        let tetelek = [];


        let felhasznalo =  $("#user-email").val(); 
        let jelszo = $("#pwd").val();
        let szamlaagentkulcs = $("#agentkey").val();
        let pdfLetoltes = $("#pdfDownload").val();
        let fizmod = $("#fizmod").val();
        let penznem = $("#penznem").val();
        let pdfSablon = $("#sablon").val();

        $("#tetelek tbody input[name^='megnevezes']").each(function () {
          let tetelId = $(this).attr("data-megnevezes-id");
          
          tetel = {
            megnevezes : $("#megnevezes" + tetelId).val(),
            mennyiseg : $("#mennyiseg" + tetelId).val(),
            mennyisegiEgyseg : $("#mennyisegegyseg" + tetelId).val(),
            nettoEgysegar : $("#egysegar" + tetelId).val(),
            netto : $("#netto" + tetelId).val(),
            afakulcs : $("#afakulcs" + tetelId).val(),
            afa : $("#afa" + tetelId).val(),
            brutto : $("#brutto" + tetelId).val()
          }
          tetelek.push(tetel);
             
            
        });




        var formData = {
            felhasznalo : felhasznalo,
            jelszo : jelszo,
            szamlaagentkulcs: szamlaagentkulcs,
            pdfLetoltes : pdfLetoltes,
            fizmod : fizmod,
            penznem : penznem,
            pdfSablon : pdfSablon,
            tetelek : tetelek
        }  
        
        
        //var token = $("meta[name='_csrf']").attr("content");
        //var header = $("meta[name='_csrf_header']").attr("content");
        $
        .ajax({

            type: "POST",
            contentType: "application/json",
            url: "nyugtacreate",
            data: JSON.stringify(formData),
            dataType: 'json',
            /*beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
              },*/
            success: function (result) {


                if (result.status == "success") {

                  alert(result.object);

                  console.log("Success: ", result);
                } else {

                    console.log("Fail: ", error);
                }
            },

            error: function (e) {

                console.log("ERROR: ", e);
            }
        });

       
 
 

	});
//
$("body").delegate("a[name^='btnCreateNyugtaGet']", "click", function () {
      let nyugtaszam = $(this).attr("id");
      var formData = {
        felhasznalo : $("#user-email").val() ,
        jelszo : $("#pwd").val(),
        szamlaagentkulcs: $("#agentkey").val(),
        nyugtaszam: nyugtaszam
    }  
    $
    .ajax({

        type: "POST",
        contentType: "application/json",
        url: "nyugta-get-create",
        data: JSON.stringify(formData),
        dataType: 'json',
        
        success: function (result) {


            if (result.status == "success") {

              alert(result.object);

              console.log("Success: ", result);
            } else {

                console.log("Fail: ", error);
            }
        },

        error: function (e) {

            console.log("ERROR: ", e);
        }
    });
     
});

$("body").delegate("a[name^='btnCreateNyugtaStorno']", "click", function () {
  let nyugtaszam = $(this).attr("id");
  var formData = {
    felhasznalo : $("#user-email").val() ,
    jelszo : $("#pwd").val(),
    szamlaagentkulcs: $("#agentkey").val(),
    nyugtaszam: nyugtaszam
}  
$
.ajax({

    type: "POST",
    contentType: "application/json",
    url: "nyugta-storno-create",
    data: JSON.stringify(formData),
    dataType: 'json',
    
    success: function (result) {


        if (result.status == "success") {

          alert(result.object);

          console.log("Success: ", result);
        } else {

            console.log("Fail: ", error);
        }
    },

    error: function (e) {

        console.log("ERROR: ", e);
    }
});
 
});

  
 $("body").delegate("a[name^='btnOpenNyugta']", "click", function () {
    let filename = $(this).attr("id");
    var formData = {
            id: '',
            nyugtaszam:'',
            kelt:'',
            filename: filename 
    }  
  $
  console.log(formData);

     $.ajax({
  
      type: "POST",
      contentType: "application/json",
      url: "nyugta-open",
      data: JSON.stringify(formData),
      dataType: 'json',
      
      success: function (result) {
  
  
          if (result.status == "success") {
            let content = "<table style='width:100%;'>";
            content = content +  "<tr><td colspan='4' style='text-align:right;font-weight:bold;'>NYUGTA</td></tr>";
            content = content +  "<tr><td colspan='2' style='text-align:left;width:50%;'>Nyugtaszán:"+ result.object.nyugtaszam +"</td>";
            content = content +  "<td colspan='2' style='text-align:right;width:50%;'>Kelte:"+ result.object.kelte +"</td></tr>";
            content = content +  "<tr><td colspan='4' style='text-align:right;font-weight:bold;'>&nbsp;</td></tr>";


            content = content +  "<tr><td style='text-align:left;font-weight:bold;'>Megnevezés</td>";
            content = content + "<td style='text-align:left;font-weight:bold;'>Mennyiség</td>";
            content = content + "<td style='text-align:left;font-weight:bold;'>Egységár</td>"
            content = content + "<td style='text-align:left;font-weight:bold;'>Bruttó ár</td></tr>";

            $.each(result.object.tetelekshortList, function (i, tetel) {

                content = content + "<tr><td>" + tetel.megnevezes + "</td>" +
                    "<td>" + tetel.mennyiseg + "</td>" +
                    "<td>" + tetel.nettoEgysegar + "</td>" +
                    "<td>" + tetel.brutto + "</td>" +
                    "</tr>";


            });
            content = content +  "<tr><td colspan='2' style='text-align:left;width:50%;'>Összesen:</td>";
            content = content +  "<td colspan='2' style='text-align:right;width:50%;'>"+ result.object.brutto +"</td></tr>";

            content+= "</table>"
            $("#nyugtaContent").html(content);
            $("#nyugtakep").modal("show");
            console.log("Success: ", result);
          } else {
  
              console.log("Fail: ", error);
          }
      },
  
      error: function (e) {
  
          console.log("ERROR: ", e);
      }
  });
   
  });



});