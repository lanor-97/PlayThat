$(document).ready(function()  {
	var playlist_option = "manage";
	
	$("#create-choice").hide();
	$("#manage-choice").hide();
	$("#favourite-choice").hide();
	$("#favourite-songs-list").hide();
	
	//Selected "crea playlist"
	$("#create-playlist").click(function()  {
		$("#create-choice").toggle();
		$("#create-playlist").toggleClass("list-group-item-info");
		$("#create-playlist").toggleClass("list-group-item-success");
	})

	//Selected "gestisci le tue playlist"
	$("#manage-playlist").click(function()  {
		
		if($("#manage-choice").is(":hidden")) {
			$.ajax({
			    url: "showPlaylist",
			    type: "GET"
			}).done(function(list)  {
				show_my_playlists(JSON.parse(JSON.stringify(list)));
			})
		}
		
		$("#manage-choice").toggle();
		$("#manage-playlist").toggleClass("list-group-item-info");
		$("#manage-playlist").toggleClass("list-group-item-success");
	})

	//Selected "playlist preferite"
	$("#favourite-playlist").click(function()  {
    
    	if($("#favourite-choice").is(":hidden")) {
    		$.ajax({
			    url: "showFavouritePlaylist",
			    type: "GET"
    		}).done(function(list)  {
    			show_my_favourite_playlists(JSON.parse(JSON.stringify(list)));
    		})
    	}
    
		$("#favourite-choice").toggle();
		$("#favourite-playlist").toggleClass("list-group-item-info");
		$("#favourite-playlist").toggleClass("list-group-item-success");
	})
	
	//crea playlist nel DB
	$("#create-playlist-action").click(function()  {
		var name_playlist = $("#name-playlist-create").val();
		$.ajax({
		    url: "createPlaylist",
		    type: "POST",
		    data: { name: name_playlist }
		}).done(function( e ) {
			if(e == "success")  {
				swal({
					type: 'success',
					title: 'Fatto',
					text: 'Playlist creata',
					confirmButtonText: 'Ok'
				})
			} else if(e == "same_name")  {
				swal({
					type: 'error',
					title: 'Ops..',
					text: 'Nome uguale ad un\'altra tua playlist',
					confirmButtonText: 'Riprova'
				})
			} else if(e == "invalid_name")  {
				swal({
					type: 'error',
					title: 'Ops..',
					text: 'Nome playlist non valido',
					confirmButtonText: 'Riprova'
				})
			}
		})
	})
	
	//Selected mostra brani preferiti
	$("#show-favourite-songs").click(function()  {
		
		if($("#favourite-songs-list").is(":hidden")) {
			$.ajax({
			    url: "showFavouriteSongs",
			    type: "GET"
    		}).done(function(list)  {
    			show_my_favourite_songs(JSON.parse(JSON.stringify(list)));
    		})
		}
		
		$("#favourite-songs-list").toggle();
		$("#show-favourite-songs").toggleClass("btn-success");
		$("#show-favourite-songs").toggleClass("btn-primary");
	})
})

//GESTIONE PLAYLIST

//mostra nomi playlists
function show_my_playlists(playlist_list)  {
	var html = "";
	$("#manage-choice").empty();
	
	for (var i = 0; i < playlist_list.length; i++) {
		var nome = playlist_list[i].name;
		var id = playlist_list[i].id;
		html += "<li class=\"list-group-item list-group-item-secondary\">\n";
		html += "<h3 class=\"pointer\" onclick=\"show_playlist_songs_action($(this).text())\">" + nome + " (ID: " + id + ")" + "</h3>\n";
		html += "<button type=\"button\" class=\"btn btn-labeled btn-success\" onclick=\"rename_playlist($(this).prev().text())\" data-toggle=\"modal\" data-target=\"#rename-modal\">Rinomina</button>\n";
		html += "<button type=\"button\" class=\"btn btn-labeled btn-danger\" onclick=\"delete_playlist($(this).prev().prev().text())\">Elimina</button></li>\n";
	}
		
	$("#manage-choice").append(html);
}

//rinomina mia playlist (ricarica pagina alla fine)
function rename_playlist(playlist_name)  {
	$("#rename-actual-name").text(playlist_name);
	
	$("#rename-playlist-action").click(function()  {
		var old_name = $("#rename-actual-name").text();
		var new_name = $("#new-name-playlist").val();
		
		if(new_name == "")  {
			swal({
				type: 'error',
				title: 'Ops..',
				text: 'Nome playlist non valido',
				confirmButtonText: 'Riprova'
			})
			return;
		}
		
		$.ajax({
		    url: "renamePlaylist",
		    type: "POST",
		    data: { oldName: old_name, newName: new_name }
		}).done(function( e ) {
			if(e == "success")  {
				swal({
					type: 'success',
					title: 'Fatto',
					text: 'Playlist rinominata',
					confirmButtonText: 'Ok'
				})
				
				location.reload();
			} else if(e == "same_name")  {
				swal({
					type: 'error',
					title: 'Ops..',
					text: 'Nome uguale ad un\'altra tua playlist',
					confirmButtonText: 'Riprova'
				})
			} else if(e == "invalid_name")  {
				swal({
					type: 'error',
					title: 'Ops..',
					text: 'Nome playlist non valido',
					confirmButtonText: 'Riprova'
				})
			}
		})
	})
}

//elimina mia playlist
function delete_playlist(playlist_name)  {
	$.ajax({
	    url: "deletePlaylist",
	    type: "POST",
	    data: { name_id: playlist_name }
	}).done(function( e ) {
		if(e == "success")  {
			swal({
				type: 'success',
				title: 'Fatto',
				text: 'Playlist cancellata',
				confirmButtonText: 'Ok'
			})
			$("#songs-list").empty();
			$("#manage-choice").toggle();
			$("#manage-playlist").toggleClass("list-group-item-info");
			$("#manage-playlist").toggleClass("list-group-item-success");
		}
	})
}

//mostra brani playlist selezionata
function show_playlist_songs_action(playlist_name_id)  {
	
	//formatto la stringa
	var playlist_id = playlist_name_id.substring(playlist_name_id.indexOf("(ID:")+5, playlist_name_id.indexOf(")"));
	
	$("#name-playlist-show-songs").text(playlist_name_id);
		
	$.ajax({
	   url: "showPlaylistSongs",
	   type: "GET",
	   data: {
	    	playlist_id: playlist_id
	    }
	}).done(function(list)  {
		show_playlist_songs(JSON.parse(JSON.stringify(list)));
	})
	
}

//PLAYLIST PREFERITE

//mostra brani playlist selezionata
function show_my_favourite_playlists(playlist_list)  {
	var html = "";
	$("#favourite-choice").empty();
		
	for (var i = 0; i < playlist_list.length; i++) {
		var nome = playlist_list[i].name;
		var id = playlist_list[i].id;
		html += "<li class=\"list-group-item list-group-item-secondary\">\n";
		html += "<h3 class=\"pointer\" onclick=\"show_favouriteplaylist_songs_action($(this).text())\">" + nome + " (ID: " + id + ")" + "</h3>\n";
		html += "<button type=\"button\" class=\"btn btn-labeled btn-danger\" onclick=\"remove_favourite_playlist($(this).prev().text())\">Elimina</button></li>\n";
	}
		
	$("#favourite-choice").append(html);
}

//rimuove playlist selezionata dalle preferite
function remove_favourite_playlist(playlist_name)  {
	$.ajax({
	    url: "removeFavouritePlaylist",
	    type: "POST",
	    data: { name_id: playlist_name }
	}).done(function( e ) {
		if(e == "success")  {
			swal({
				type: 'success',
				title: 'Fatto',
				text: 'Playlist tolta dalle preferite',
				confirmButtonText: 'Ok'
			})
			$("#songs-list").empty();
			$("#favourite-choice").toggle();
			$("#favourite-playlist").toggleClass("list-group-item-info");
			$("#favourite-playlist").toggleClass("list-group-item-success");
		}
	})
}

//mostra brani playlist selezionata
function show_favouriteplaylist_songs_action(playlist_name_id)  {
	
	//formatto la stringa
	
	var playlist_id = playlist_name_id.substring(playlist_name_id.indexOf("(ID:")+5, playlist_name_id.indexOf(")"));
	var playlist_name = playlist_name_id.substring(0, playlist_name_id.indexOf(" (ID:"));
	
	$("#name-playlist-show-songs").text(playlist_name_id);
		
	$.ajax({
	   url: "showPlaylistSongs",
	   type: "GET",
	   data: {
	    	playlist_id: playlist_id
	    }
	}).done(function(list)  {
		show_favouriteplaylist_songs(JSON.parse(JSON.stringify(list)));
	})
	
}

//GESTIONE BRANI PLAYLIST

//elenca brani mia playlist selezionata
function show_playlist_songs(songs)  {
	
	var html = "";
	$("#songs-list").empty();
	
	$("#name-playlist-show-songs").removeClass("list-group-item-danger");
	$("#name-playlist-show-songs").addClass("list-group-item-success");
		
	for (var i = 0; i < songs.length; i++) {
		var titolo = songs[i].name;
		var id = songs[i].id;
		var author = songs[i].author.name;
		
		html += "<li class=\"list-group-item list-group-item-secondary\">\n";
		html += "<h3>" + titolo + " (ID: " + id + ")" + "</h3>\n";
		html +=	"<audio controls>\n <source src=\"songs/" + author + " - " + titolo + ".mp3\" type=\"audio/mpeg\">" + "</audio><br>\n";
		html += "<button type=\"button\" class=\"btn btn-labeled btn-danger\" onclick=\"remove_playlist_song($(this).prevUntil('h3').prev().text())\">Elimina</button></li>";
	}
	
	$("#songs-list").append(html);
}

//elenca brani playlist PREFERITA selezionata (non si posson eliminare)
function show_favouriteplaylist_songs(songs)  {
	
	var html = "";
	$("#songs-list").empty();
	
	$("#name-playlist-show-songs").removeClass("list-group-item-danger");
	$("#name-playlist-show-songs").addClass("list-group-item-success");
		
	for (var i = 0; i < songs.length; i++) {
		var titolo = songs[i].name;
		var id = songs[i].id;
		var author = songs[i].author.name;
		
		html += "<li class=\"list-group-item list-group-item-secondary\">\n";
		html += "<h3>" + titolo + " (ID: " + id + ")" + "</h3>\n"
		html +=	"<audio controls>\n <source src=\"songs/" + author + " - " + titolo + ".mp3\" type=\"audio/mpeg\">" + "</audio></li>\n";
	}
	
	$("#songs-list").append(html);
}

//rimuove brano dalla playlist MIA
function remove_playlist_song(song_name)  {
	
	var name_playlist_id =$("#name-playlist-show-songs").text();
	var name_playlist = name_playlist_id.substring(0, name_playlist_id.indexOf(" (ID:"));
	var playlist_id = name_playlist_id.substring(name_playlist_id.indexOf(" (ID:")+6, name_playlist_id.length-1);
	
	var song_id = song_name.substring(song_name.indexOf(" (ID:")+6, song_name.length-1);
	
	$.ajax({
	    url: "removePlaylistSong",
	    type: "POST",
	    data: { song_id: song_id,
	    		playlist_id: playlist_id
	    	}
	}).done(function( e ) {
		if(e == "success")  {
			swal({
				type: 'success',
				title: 'Fatto',
				text: 'Brano tolto dalla playlist',
				confirmButtonText: 'Ok'
			})
			
			show_playlist_songs_action(name_playlist_id);
		}
	})
}

//GESTIONE BRANI PREFERITI

//cliccando su mostra, elenca i brani preferiti
function show_my_favourite_songs(songs)  {
	var html = "";
	$("#favourite-songs-list").empty();
		
	for (var i = 0; i < songs.length; i++) {
		var titolo = songs[i].name;
		var id = songs[i].id;
		var author = songs[i].author.name;
		
		html += "<li class=\"list-group-item list-group-item-info\"><h3>" + titolo + " (ID: " + id + ")</h3></li>\n";
		html +=	"<audio controls>\n <source src=\"songs/" + author + " - " + titolo + ".mp3\" type=\"audio/mpeg\">" + "</audio><br>";
		html += "<button style=\"margin-bottom: 30px\" type=\"button\" class=\"btn btn-danger btn-md\" onclick=\"remove_favourite_song($(this).prevUntil('h3').prev().text())\">Elimina</button>\n";
	}
		
	$("#favourite-songs-list").append(html);
}

//rimuove un brano dai preferiti
function remove_favourite_song(song_name)  {
	$.ajax({
	    url: "removeFavouriteSong",
	    type: "POST",
	    data: { name_id: song_name }
	}).done(function( e ) {
		if(e == "success")  {
			swal({
				type: 'success',
				title: 'Fatto',
				text: 'Brano tolto dai preferiti',
				confirmButtonText: 'Ok'
			})
		}
	})
	
	$("#favourite-songs-list").toggle();
	$("#show-favourite-songs").toggleClass("btn-success");
	$("#show-favourite-songs").toggleClass("btn-primary");
}
