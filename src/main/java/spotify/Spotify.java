package spotify;

import feign.Param;
import feign.RequestLine;
import spotify.model.PlaylistObject;

interface Spotify  {

    @RequestLine("GET /v1/playlists/{playlist_id}")
    PlaylistObject getPlaylist(@Param("playlist_id") final String playlistId);

}
