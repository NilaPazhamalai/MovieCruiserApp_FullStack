import { Component, OnInit } from '@angular/core';
import { MovieService } from '../../movie.service';
import { Location } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Movie } from '../../movie';
import { ThumbnailComponent } from '../thumbnail/thumbnail.component'
import { MatSnackBar } from '@angular/material/snack-bar'
import { AuthenticationService } from '../../../authentication/authentication.service';
@Component({
  selector: 'app-detail',
  templateUrl: './detail.component.html',
  styleUrls: ['./detail.component.css']
})
export class DetailComponent implements OnInit {

  movieDetail: Movie;
  movieDetailTmdb: Movie;
  movieDetailSpring: Movie;
  movieId: number;
  useWatchlistApi: boolean;

  constructor(
    private movieService: MovieService,
    private location: Location,
    private actRoute: ActivatedRoute,
    private matSnackBar: MatSnackBar,
    private authService: AuthenticationService) {

    this.actRoute.params.subscribe(
      (params) => {

        this.movieId = params['id'];
        this.useWatchlistApi = params['useWatchlistApi'] === 'true';
        console.log('movieId = ' + this.movieId);
        console.log(params['useWatchlistApi'] + '  ===> watchlist api = ' + this.useWatchlistApi);
      }
    );

    /* this.movieId = this.actRoute.snapshot.params['id'];
    this.useWatchlistApi = this.actRoute.snapshot.params['useWatchlistApi'];
    console.log('movieId = ' + this.movieId);
    console.log('api = ' + this.useWatchlistApi); */

    if (this.useWatchlistApi) {
      console.log('api = ' + this.useWatchlistApi);
      this.movieService.getMovie(this.movieId).
        subscribe(
        (movie) => {
          console.log("movie received from sql :" + movie);
          this.movieDetail = movie;
        }
        );
    } else {
      console.log('tmdb = ' + this.useWatchlistApi);
      this.movieService.getMovieDetail(this.movieId).
        subscribe(
        (movie) => {
          console.log("movie received from tmdb :" + movie);
          let transformMovie = <Movie>{};
          transformMovie.movie_id = movie.id;
          transformMovie.title = movie.title;
          transformMovie.overview = movie.overview;
          transformMovie.comments = movie.comments;
          transformMovie.poster_path = movie.poster_path;
          transformMovie.release_date = movie.release_date;
          transformMovie.detailId = movie.id;
          this.movieDetail = transformMovie;
        }
        );
    }
  }

  ngOnInit() {

  }

  goBack() {
    this.location.back();
  }

  saveComments(comments: string) {
    this.movieDetail.comments = comments;
    this.movieService.updateMovie(this.movieDetail).subscribe(
      (movie) => {
        this.matSnackBar.open('Comments added successfully', '', {
          duration: 1000
        });
      }
    );
  }


  deleteFromWatchList() {
    this.movieService.deleteMovie(this.movieDetail).subscribe(
      (resString) => {
        console.log(resString);
        this.matSnackBar.open(resString, '', {
          duration: 1000
        });
        this.location.back();
      }
    );
  }


  addToWatchList(): void {
    let message = `${this.movieDetail.title} added to WatchList`;
    console.log('movie Name' + message + this.movieDetail.movie_id);
    this.movieService.saveWatchListMovies(this.movieDetail).subscribe(
      (movie) => {
        this.matSnackBar.open(message, '', {
          duration: 1000
        });
      }
    );
  }

}
