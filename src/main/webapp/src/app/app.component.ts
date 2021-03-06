// Copyright 2020 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

import {Component} from '@angular/core';
import {Router} from '@angular/router';

const LOGIN_PAGE: String = '/page/login';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css', './common/growpod-page-styles.css'],
})
export class AppComponent {
  constructor(private router: Router) {}

  isLoggedIn(): boolean {
    return this.router.url !== LOGIN_PAGE;
  }
}
