// execute at page load
// jquery hide & show : https://www.w3schools.com/jquery/jquery_hide_show.asp
/*async function recupListeMediums() {
    try {
        const res = await $.ajax({
            url: './ActionServlet',
            method:'POST',
            data: {
                todo:'lister-mediums'
            },
            dataType: 'json'
        })
        .done((response) => {
            console.log(response);

            // inject elements inside listMediums section
            $.each(response.mediums, function(index, medium) {
                console.log(medium.denomination);
                $('#table_list_medium').append(
                    '<tr>'+
                        '<td>'+
                            medium.denomination+
                        '</td>'+
                        '<td>'+
                            medium.type+
                        '</td>'+
                        '<td>'+
                            medium.presentation+
                        '</td>'+
                    '</tr>'
                );
            });
        })
        .fail((error) => {
            console.log('erreur requête lister-mediums', error);
        });

    } catch (err) {
        console.log(err);
    }
}*/

function recupListeMediums() {
    $.ajax({
        url: './ActionServlet',
        method:'POST',
        data: {
            todo:'lister-mediums'
        },
        dataType:'json'
    }).done(function (response) {
        console.log(response);
        $('#table_list_medium').empty();
        $('#ta').append(
            '<table id="table_list_medium">'+
                '<tr>'+
                    '<th>Denomination</th>'+
                    '<th>Spécialité</th>'+
                    '<th>Présentation</th>'+
                '</tr>'+
            '</table>'
        );
        $.each(response.mediums, function(index,medium) {
            console.log(medium.denomination);
            $('#liste-mediums').append(
              '<tr>'+'<td>'+
              "Denomination : "+medium.denomination+" | Presentation : "+medium.presentation +
              '</td>'+'</tr>'
            );
        });
    })
    .fail(function(error) {
        console.log('erreur déso',error);
    });                
}

function pageInit() {
    $(".hide").hide(); // hide selected sections at the beginning
    recupListeMediums();
}

$(document).ready(pageInit());

// button functions
async function voirMediums() {
    console.log("[debug] clic sur 'Voir nos médiums'");
    $("#list_mediums").show();

    // hide button
    $("#voir_mediums").hide();
}


