import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateProductListComponent } from './update-product-list.component';

describe('UpdateProductListComponent', () => {
  let component: UpdateProductListComponent;
  let fixture: ComponentFixture<UpdateProductListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UpdateProductListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UpdateProductListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
