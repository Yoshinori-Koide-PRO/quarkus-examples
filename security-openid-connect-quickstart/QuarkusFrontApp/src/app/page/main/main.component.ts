import { Component, OnInit } from '@angular/core';
import { DefaultService, Fruit } from 'src/app/quarkus-api';
import { Observable, BehaviorSubject, Subject } from 'rxjs';
import { KeycloakService } from 'keycloak-angular';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.scss']
})
export class MainComponent implements OnInit {

  private fruits: Subject<Array<Fruit>> = new BehaviorSubject<Array<Fruit>>([]);

  constructor(private keycloak: KeycloakService, private aipGateway: DefaultService) { }

  ngOnInit() {
    this.aipGateway.fruitsGet('body').subscribe(fruits => {
      console.log(`simple:${fruits}`);
      console.log(`json:${JSON.stringify(fruits, null, "  ")}`);
      this.fruits.next(fruits);
    })
  }

  async logOut() {
    await this.keycloak.logout();
  }
}
