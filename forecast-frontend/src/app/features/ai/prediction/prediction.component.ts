import { Component, Input, AfterViewInit, ElementRef, ViewChild, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HostListener } from '@angular/core';
import { registerables } from 'chart.js';
import Chart from 'chart.js/auto';
import ChartDataLabels from 'chartjs-plugin-datalabels';


// Registrar tudo uma única vez (ideal fora do componente, mas aqui funciona)
Chart.register(...registerables, ChartDataLabels);

@Component({
  selector: 'app-prediction',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './prediction.component.html',
  styleUrl: './prediction.component.css'
})
export class PredictionComponent implements AfterViewInit, OnDestroy {
  @Input() previsaoData: { mes: string; vendas: number; taxa: number }[] = [];

  @ViewChild('chartCanvasVendas') chartCanvasVendas!: ElementRef<HTMLCanvasElement>;
  @ViewChild('chartCanvasTaxa') chartCanvasTaxa!: ElementRef<HTMLCanvasElement>;

  private chartVendas?: Chart;
  private chartTaxa?: Chart;

  @HostListener('window:resize', ['$event'])
  onResize(event: Event) {
    this.chartVendas?.resize();
    this.chartTaxa?.resize();
  }

  ngAfterViewInit() {
    console.log('[PredictionComponent] ngAfterViewInit chamado');
    console.log('[PredictionComponent] Dados recebidos:', this.previsaoData);

    // Só cria se tiver dados e o canvas existir
    if (this.previsaoData?.length > 0 && this.chartCanvasVendas?.nativeElement && this.chartCanvasTaxa?.nativeElement) {
      this.createOrUpdateChart();
    }

  }

  private createOrUpdateChart() {

  if (this.chartVendas) {
    this.chartVendas.destroy();
  }

  if (this.chartTaxa) {
    this.chartTaxa.destroy();
  }

  // GRÁFICO DE VENDAS
  this.chartVendas = new Chart(this.chartCanvasVendas.nativeElement, {
    type: 'bar',
    data: {
      labels: this.previsaoData.map(item => item.mes),
      datasets: [{
        label: 'Vendas Previstas',
        data: this.previsaoData.map(item => item.vendas),
        backgroundColor: 'rgba(54, 162, 235, 0.65)',
        borderColor: 'rgba(54, 162, 235, 1)',
        borderWidth: 1
      }]
    },
    options: {
      responsive: true,
      maintainAspectRatio: false
    },
    plugins: [ChartDataLabels]
  });

  // GRÁFICO DE TAXA
  this.chartTaxa = new Chart(this.chartCanvasTaxa.nativeElement, {
    type: 'line',
    data: {
      labels: this.previsaoData.map(item => item.mes),
      datasets: [{
        label: 'Taxa de Crescimento (%)',
        data: this.previsaoData.map(item => item.taxa),
        borderColor: 'rgba(255, 99, 132, 1)',
        backgroundColor: 'rgba(255, 99, 132, 0.2)',
        tension: 0.3
      }]
    },
    options: {
      responsive: true,
      maintainAspectRatio: false
    }
  });
}

  ngOnDestroy() {
    if (this.chartVendas) {
      this.chartVendas.destroy();
    }
    if (this.chartTaxa) {
      this.chartTaxa.destroy();
    }
  }
}