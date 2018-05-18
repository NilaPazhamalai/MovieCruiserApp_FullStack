import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ThumbnailComponent } from './components/thumbnail/thumbnail.component';


import { MovieService } from './movie.service';

import { ContainerComponent } from './components/container/container.component';
import { MovieRoutingModule   } from './movie-router.module';
import { TmdbContainerComponent } from './components/tmdb-container/tmdb-container.component';
import { WatchlistComponent } from './components/watchlist/watchlist.component';
import {SearchComponent} from './components/search/search.component'
import { DetailComponent } from './components/detail/detail.component'

import { MatCardModule} from '@angular/material/card'
import { MatButtonModule} from '@angular/material/button'
import {MatSnackBarModule} from '@angular/material/snack-bar'
import { MatIconModule}  from '@angular/material/icon'
import { MatFormFieldModule } from '@angular/material';
import { MatInputModule } from '@angular/material';
import {MatGridListModule} from '@angular/material'

@NgModule({
  imports: [
    CommonModule,
    MovieRoutingModule,
    MatCardModule,
    MatButtonModule,
    MatSnackBarModule,
    MatIconModule,
    MatFormFieldModule,
    MatInputModule,
    MatGridListModule
  ],
  declarations: 
    [ThumbnailComponent, ContainerComponent,TmdbContainerComponent,WatchlistComponent, 
      SearchComponent, DetailComponent],
  exports:
    [ThumbnailComponent, ContainerComponent,TmdbContainerComponent,WatchlistComponent,
      SearchComponent,DetailComponent, MovieRoutingModule],
  providers:
    [MovieService]
})
export class MovieModule { }
