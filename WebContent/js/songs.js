$(document).ready(function()  {
	
	//uso una variabile globale per tenere conto di quale canzone è presa in considerazione
	var song_selected = "";
	
	$(".song-card").click(function()  {
		song_selected = $(this).attr("id");
	})
	
	//nasconto quest'opzione all'inizio
	$("#if-playlist-selected").hide();
	
	//work in progress
	$(".play-song").click(function()  {
		var song_id = $(this).parent().attr("id");
	})
	
	//se seleziona playlist, gli faccio scegliere da una lista delle sue playlist
	$('#where-add-to').change(function() {
	    var sel = $(this).val();
	    
		if(sel == "Mia playlist")  {
			$('#if-playlist-selected').show();
		} else  {
			$('#if-playlist-selected').hide();
		}
	});
	
	//decide di aggiungere un det. brano ad una det. libreria
	$("#add-song-action").click(function()  {
		var song_id = song_selected;
		var dest = "";
		var play = "";
		if($("#where-add-to").val() == "Brani preferiti")  {
			dest = "BraniPreferiti";
		} else  {
			var pname = $("#who-add-to").val();
			if(pname == "" || pname == null)  {
				swal({
					type: 'error',
					title: 'Ops..',
					text: 'errore',
					confirmButtonText: 'Riprova'
				})
				return;
			}
			
			dest = "Playlist";
			play = pname;
		}
		
		$.ajax({
			url: "addSongLibrary",
			type: "POST",
			data: {
				song: song_id,
				destination: dest,
				playlist: play
			}
		}).done(function(e)  {
			if(e == "success")  {
				swal({
					type: 'success',
					title: 'Fatto',
					text: 'Aggiunto con successo',
					confirmButtonText: 'Ok'
				})
			} else if(e == "fail")  {
				swal({
					type: 'error',
					title: 'Ops..',
					text: 'Qualcosa è andato storto',
					confirmButtonText: 'Riprova'
				})
			}
		})
	})
})

function add_favourite_playlist(playlist_id)  {
	$.ajax({
		url: "addFavouritePlaylist",
		type: "POST",
		data: {
			playlist: playlist_id
		}
	}).done(function(e)  {
		if(e == "success")  {
			swal({
				type: 'success',
				title: 'Fatto',
				text: 'Aggiunta alle preferite',
				confirmButtonText: 'Ok'
			})
		}
	})
}

