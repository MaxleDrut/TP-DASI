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
                            '<div class="user_img">'+
                                '<img src="img/user.svg" height="40"/>'+
                            '</div>'+
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
        if(response.mediums.length >= 2) {
            for(let i=0; i<2; i++) {
                $('#front_mediums').append(
                    '<div>'+
                        '<div>'+
                            '<div class="user_img">'+
                                '<img src="img/user.svg" height="40"/>'+
                            '</div>'+ 
                        '<div>'+
                        '<p>'+
                            response.mediums[i].denomination+
                        '</p>'+
                        '<p>'+
                            response.mediums[i].type+
                        '</p>'+
                    '</div>'
                );
            }
        } else {
            console.log("No data in response/mediums");
        }
        
    })
    .fail(function(error) {
        console.log('erreur déso',error);
    });               
}

function pageInit() {
    $(".hide").hide(); // hide selected sections WARN: do it at last
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


// [html loader]
/** useless : use insteal jquery.load() !!!
$(document).ready(() => {
    // add login nav bar from file
    let filepath = (document.URL + "login.html");
    console.log(filepath);
    fetch(filepath)
        .then((response) => {
            // The API call was successful!
	        return response.text();
        })
        .then((html) => {
            console.log(html);
            // Convert the HTML string into a document object
            var parser = new DOMParser();
            var doc = parser.parseFromString(html, 'text/html');
            // add html code to page
            //$('#login_top_nav').append(html);
        })
        .catch(function (err) {
            // There was an error
            console.warn('Something went wrong.', err);
    });
});*/

