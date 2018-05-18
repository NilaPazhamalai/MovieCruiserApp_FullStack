import { TestBed, inject } from '@angular/core/testing';

import { MovieHttpService } from './movie-http.service';

describe('MovieHttpService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [MovieHttpService]
    });
  });

  it('should be created', inject([MovieHttpService], (service: MovieHttpService) => {
    expect(service).toBeTruthy();
  }));
});
