
function pageInit() {
    // hide selected sections at the beginning
    $(".hide").hide();
}

$(document).ready(pageInit());

async function voirMediums() {
    console.log("[debug] clic sur 'Voir nos médiums'");
    try {
        const res = await $.ajax({
            url: './ActionServlet?action=',
            data: {
                todo:'lister-mediums'
            },
            method: 'GET',
            dataType: 'json'
        })
        console.log(res);

    } catch (err) {
        console.log(err);
    }


    // inject elements inside listMediums section
    $("#list_mediums").show();
}


