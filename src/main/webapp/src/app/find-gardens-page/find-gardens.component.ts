import {Component, OnInit} from '@angular/core';
import {OwlOptions} from 'ngx-owl-carousel-o';
@Component({
  selector: 'FindGardens',
  templateUrl: './find-gardens.component.html',
  styleUrls: ['./find-gardens.component.css'],
})
export class FindGardensComponent implements OnInit {
  /* Option to display a certian number of slides based off of browser window size along with
   ** some style options
   */
  customOptions: OwlOptions = {
    responsive: {
      0: {
        items: 1,
      },
      400: {
        items: 2,
      },
    },
    nav: true,
    margin: 20,
  };
  ngOnInit() {}
}
