import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-market-indicators',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './market-indicators.component.html'
})
export class MarketIndicatorsComponent implements OnInit {

  indicators: any[] = [];
  loading = true;
  lastUpdate: string = '';

  private apiUrl = 'https://api-gateway-ptj6.onrender.com/ai/market/indicators';

  constructor() {}

  ngOnInit() {
    this.loadIndicators();
    // Atualiza a cada 60 segundos
    setInterval(() => this.loadIndicators(), 60000);
  }

  async loadIndicators() {
    try {
      const res = await fetch(this.apiUrl);
      if (res.ok) {
        this.indicators = await res.json();
        this.lastUpdate = new Date().toLocaleTimeString('pt-BR');
      }
    } catch (error) {
      console.error('Erro ao carregar indicadores', error);
    } finally {
      this.loading = false;
    }
  }

  getColor(change: number): string {
    return change >= 0 ? 'text-green-600' : 'text-red-600';
  }
}
