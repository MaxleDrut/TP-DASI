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

        // put table header
        $('#table_list_medium').empty();
        $('#table_list_medium').append(
                '<tr>'+
                    '<th>Denomination</th>'+
                    '<th>Spécialité</th>'+
                    '<th>Présentation</th>'+
                '</tr>'
        );
        // populate table_list_medium
        $.each(response.mediums, function(index,medium) {
            console.log(medium.denomination);
            $('#table_list_medium').append(
                '<tr>'+
                    '<td>'+
                        '<div>'+
                            '<img src="img/icone_bonhomme.PNG" height="40"/>'+ 
                        '</div>'+
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

        // populate front_mediums
        for(let i=0; i<2; i++) {
            $('#front_mediums').append(
                '<div>'+
                    '<div>'+
                        '<img src="img/icone_bonhomme.PNG" height="40"/>'+ 
                    '<div>'+
                    '<p>'+
                        response.mediums[i].denomination+
                    '<p>'+
                    '<p>'+
                        response.mediums[i].type+
                    '<p>'+
                '</div>'
            );
        }
        $.each(response.mediums, function(index,medium) {
            console.log(medium.denomination);
            
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

