import { Component, OnInit, Input,Output,EventEmitter } from '@angular/core';
import { Movie } from '../../movie';
import { MovieService } from '../../movie.service';
import { MatSnackBar } from '@angular/material/snack-bar'
import { AuthenticationService } from '../../../authentication/authentication.service';


@Component({
  selector: 'movie-thumbnail',
  templateUrl: './thumbnail.component.html',
  styleUrls: ['./thumbnail.component.css'],
})
export class ThumbnailComponent implements OnInit {

  @Input()
  movie: Movie;
  @Input()
  useWatchlistApi : boolean;
  @Output()
  sendUpdateMovies = new EventEmitter();
  
  constructor(private movieService: MovieService, private matSnackBar: MatSnackBar, private authService:AuthenticationService) {
    
    console.log('useWatchlistApi' + this.useWatchlistApi);
  }

  ngOnInit() {

  }

  addToWatchList(): void {
    let message =`${this.movie.title} added to WatchList`;
    
    this.movieService.saveWatchListMovies(this.movie).subscribe(
      (movie) => {
        this.matSnackBar.open(message, '', {
          duration: 1000
        });
        if(this.useWatchlistApi){
          this.sendUpdateMovies.emit('sendUpdateMovies');
        }
      }
    );
  }


  deleteFromWatchList():void{
    let message =`${this.movie.title} deleted from your WatchList`;
    console.log('movie Name' + message + this.movie.id);
    this.movieService.deleteMovie(this.movie).subscribe(
      (movie) => {
        this.matSnackBar.open(message, '', {
          duration: 1000
        });
        if(this.useWatchlistApi){
          this.sendUpdateMovies.emit('sendUpdateMovies');
        }
      }
    );
  }

}
