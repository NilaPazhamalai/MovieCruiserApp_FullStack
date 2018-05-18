import { Component, OnInit, Input,Output,EventEmitter } from '@angular/core';
import { Movie } from '../../movie';
import { MovieService } from '../../movie.service';
@Component({
  selector: 'movie-container',
  templateUrl: './container.component.html',
  styleUrls: ['./container.component.css']
})
export class ContainerComponent implements OnInit {

  @Input()
  movies: Array<Movie>;
  @Input()
  useWatchlistApi : boolean;
  @Output()
  updateMovies = new EventEmitter();
  
  
  constructor() { 
    
  }

  ngOnInit() {
  }

  sendUpdateMovies(e):void{
    console.log('update trigger received in container');
    console.log('update emit from container')
    this.updateMovies.emit('updateMovies');
  }

}
