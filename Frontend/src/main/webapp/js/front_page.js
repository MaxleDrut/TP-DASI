
function pageInit() {
    // hide selected sections at the beginning
    $(".hide").hide();
}

$(document).ready(pageInit());


async function voirMediums() {
    console.log("clic sur 'Voir nos médiums'");
    /*const res = await $.ajax({
        url: './ActionServlet?action=get_mediums',
        method: 'GET',
        dataType: 'json'
    })*/

    // inject elements inside listMediums section
    $("#list_mediums").show();
}


