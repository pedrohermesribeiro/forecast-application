import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MarketIndicatorsComponent } from './market-indicators.component';

describe('MarketIndicatorsComponent', () => {
  let component: MarketIndicatorsComponent;
  let fixture: ComponentFixture<MarketIndicatorsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MarketIndicatorsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MarketIndicatorsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
