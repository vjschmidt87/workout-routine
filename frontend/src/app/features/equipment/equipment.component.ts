import { Component, inject, signal, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';
import { ApiService, EquipmentItem } from '../../core/services/api.service';
import { TranslationService } from '../../core/services/translation.service';

interface EquipmentOption {
  name: string;
  category: string;
  key: string;
}

const EQUIPMENT_OPTIONS: EquipmentOption[] = [
  { name: 'Barbell',               category: 'Free Weights', key: 'BARBELL' },
  { name: 'Dumbbells',             category: 'Free Weights', key: 'DUMBBELLS' },
  { name: 'Kettlebell',            category: 'Free Weights', key: 'DUMBBELLS' },
  { name: 'EZ Bar',                category: 'Free Weights', key: 'BARBELL' },
  { name: 'Weight Plates',         category: 'Free Weights', key: 'BARBELL' },
  { name: 'Cable Machine',         category: 'Machines',     key: 'CABLES' },
  { name: 'Leg Press Machine',     category: 'Machines',     key: 'MACHINE' },
  { name: 'Leg Extension Machine', category: 'Machines',     key: 'MACHINE' },
  { name: 'Leg Curl Machine',      category: 'Machines',     key: 'MACHINE' },
  { name: 'Lat Pulldown Machine',  category: 'Machines',     key: 'CABLES' },
  { name: 'Chest Press Machine',   category: 'Machines',     key: 'MACHINE' },
  { name: 'Pec Deck',              category: 'Machines',     key: 'MACHINE' },
  { name: 'Pull-up Bar',           category: 'Accessories',  key: 'PULL_UP_BAR' },
  { name: 'Bench',                 category: 'Accessories',  key: 'BENCH' },
  { name: 'Dip Bars',              category: 'Accessories',  key: 'BODYWEIGHT' },
  { name: 'Resistance Bands',      category: 'Accessories',  key: 'RESISTANCE_BANDS' },
  { name: 'Ab Wheel',              category: 'Accessories',  key: 'BODYWEIGHT' },
  { name: 'Foam Roller',           category: 'Accessories',  key: 'BODYWEIGHT' },
];

const EQUIPMENT_SVGS: Record<string, string> = {
  'Barbell': `<svg viewBox="0 0 80 48" xmlns="http://www.w3.org/2000/svg">
    <rect x="15" y="22" width="50" height="4" rx="2" fill="#4f8ef7" opacity=".5"/>
    <rect x="11" y="18" width="5"  height="12" rx="1.5" fill="#6aa3ff"/>
    <rect x="3"  y="14" width="9"  height="20" rx="2"   fill="#4f8ef7"/>
    <rect x="64" y="18" width="5"  height="12" rx="1.5" fill="#6aa3ff"/>
    <rect x="68" y="14" width="9"  height="20" rx="2"   fill="#4f8ef7"/>
  </svg>`,

  'Dumbbells': `<svg viewBox="0 0 80 48" xmlns="http://www.w3.org/2000/svg">
    <rect x="3"  y="15" width="5"  height="18" rx="1.5" fill="#4f8ef7"/>
    <rect x="7"  y="21" width="14" height="6"  rx="2"   fill="#6aa3ff" opacity=".8"/>
    <rect x="20" y="15" width="5"  height="18" rx="1.5" fill="#4f8ef7"/>
    <rect x="52" y="15" width="5"  height="18" rx="1.5" fill="#4f8ef7"/>
    <rect x="56" y="21" width="14" height="6"  rx="2"   fill="#6aa3ff" opacity=".8"/>
    <rect x="69" y="15" width="5"  height="18" rx="1.5" fill="#4f8ef7"/>
  </svg>`,

  'Kettlebell': `<svg viewBox="0 0 80 60" xmlns="http://www.w3.org/2000/svg">
    <circle cx="40" cy="38" r="18" fill="#4f8ef7"/>
    <circle cx="40" cy="38" r="11" fill="#1e2035"/>
    <path d="M30 24 Q28 10 33 7 Q37 4 40 5 Q43 4 47 7 Q52 10 50 24"
          fill="none" stroke="#6aa3ff" stroke-width="5" stroke-linecap="round"/>
    <rect x="34" y="4" width="12" height="5" rx="2.5" fill="#6aa3ff"/>
  </svg>`,

  'EZ Bar': `<svg viewBox="0 0 80 48" xmlns="http://www.w3.org/2000/svg">
    <rect x="3"  y="16" width="8" height="16" rx="2" fill="#4f8ef7"/>
    <rect x="69" y="16" width="8" height="16" rx="2" fill="#4f8ef7"/>
    <polyline points="11,24 20,28 30,20 40,28 50,20 60,28 69,24"
              fill="none" stroke="#6aa3ff" stroke-width="4.5" stroke-linecap="round" stroke-linejoin="round"/>
  </svg>`,

  'Weight Plates': `<svg viewBox="0 0 80 56" xmlns="http://www.w3.org/2000/svg">
    <circle cx="40" cy="28" r="24" fill="#4f8ef7"/>
    <circle cx="40" cy="28" r="17" fill="#1e2035"/>
    <circle cx="40" cy="28" r="11" fill="#4f8ef7" opacity=".4"/>
    <circle cx="40" cy="28" r="5"  fill="#1e2035"/>
    <line x1="40" y1="11" x2="40" y2="18" stroke="#6aa3ff" stroke-width="3" stroke-linecap="round"/>
    <line x1="40" y1="38" x2="40" y2="45" stroke="#6aa3ff" stroke-width="3" stroke-linecap="round"/>
    <line x1="17" y1="28" x2="24" y2="28" stroke="#6aa3ff" stroke-width="3" stroke-linecap="round"/>
    <line x1="56" y1="28" x2="63" y2="28" stroke="#6aa3ff" stroke-width="3" stroke-linecap="round"/>
  </svg>`,

  'Cable Machine': `<svg viewBox="0 0 80 72" xmlns="http://www.w3.org/2000/svg">
    <rect x="22" y="6"  width="36" height="58" rx="3" fill="#252740" stroke="#4f8ef7" stroke-width="2"/>
    <rect x="28" y="12" width="24" height="44" rx="2" fill="#4f8ef7" opacity=".18"/>
    <rect x="28" y="12" width="24" height="10" rx="2" fill="#4f8ef7" opacity=".55"/>
    <rect x="28" y="24" width="24" height="2.5" fill="#4f8ef7" opacity=".35"/>
    <rect x="28" y="29" width="24" height="2.5" fill="#4f8ef7" opacity=".35"/>
    <rect x="28" y="34" width="24" height="2.5" fill="#4f8ef7" opacity=".35"/>
    <circle cx="40" cy="8"  r="5" fill="#6aa3ff"/>
    <line x1="40" y1="13" x2="40" y2="64" stroke="#6aa3ff" stroke-width="1.5"/>
    <line x1="40" y1="64" x2="28" y2="70" stroke="#6aa3ff" stroke-width="2" stroke-linecap="round"/>
    <line x1="40" y1="64" x2="52" y2="70" stroke="#6aa3ff" stroke-width="2" stroke-linecap="round"/>
  </svg>`,

  'Leg Press Machine': `<svg viewBox="0 0 80 60" xmlns="http://www.w3.org/2000/svg">
    <line x1="6"  y1="12" x2="72" y2="52" stroke="#4f8ef7" stroke-width="3.5" stroke-linecap="round"/>
    <line x1="16" y1="10" x2="78" y2="50" stroke="#4f8ef7" stroke-width="3.5" stroke-linecap="round"/>
    <rect x="36" y="20" width="26" height="16" rx="3" transform="rotate(30 49 28)" fill="#6aa3ff"/>
    <rect x="8"  y="42" width="20" height="8"  rx="2" fill="#4f8ef7"/>
    <rect x="6"  y="36" width="8"  height="14" rx="2" fill="#6aa3ff" opacity=".7"/>
    <rect x="2"  y="54" width="76" height="5"  rx="2" fill="#252740" stroke="#4f8ef7" stroke-width="1.5"/>
  </svg>`,

  'Leg Extension Machine': `<svg viewBox="0 0 80 60" xmlns="http://www.w3.org/2000/svg">
    <rect x="10" y="8"  width="10" height="30" rx="3" fill="#4f8ef7"/>
    <rect x="8"  y="36" width="32" height="10" rx="3" fill="#6aa3ff"/>
    <line x1="38" y1="42" x2="66" y2="42" stroke="#4f8ef7" stroke-width="4.5" stroke-linecap="round"/>
    <rect x="60" y="37" width="14" height="10" rx="4" fill="#4f8ef7"/>
    <rect x="28" y="46" width="6"  height="8"  rx="1.5" fill="#4f8ef7" opacity=".6"/>
    <rect x="48" y="46" width="6"  height="8"  rx="1.5" fill="#4f8ef7" opacity=".6"/>
    <rect x="4"  y="52" width="72" height="6"  rx="2" fill="#252740" stroke="#4f8ef7" stroke-width="1.5"/>
  </svg>`,

  'Leg Curl Machine': `<svg viewBox="0 0 80 60" xmlns="http://www.w3.org/2000/svg">
    <rect x="8"  y="18" width="50" height="12" rx="3" fill="#6aa3ff"/>
    <rect x="5"  y="16" width="12" height="10" rx="3" fill="#4f8ef7" opacity=".6"/>
    <path d="M55 24 Q63 24 67 17 Q71 10 68 5"
          fill="none" stroke="#4f8ef7" stroke-width="4.5" stroke-linecap="round"/>
    <ellipse cx="68" cy="5" rx="7" ry="5" fill="#4f8ef7"/>
    <rect x="12" y="30" width="6" height="22" rx="2" fill="#4f8ef7" opacity=".5"/>
    <rect x="50" y="30" width="6" height="22" rx="2" fill="#4f8ef7" opacity=".5"/>
    <rect x="4"  y="52" width="72" height="6" rx="2" fill="#252740" stroke="#4f8ef7" stroke-width="1.5"/>
  </svg>`,

  'Lat Pulldown Machine': `<svg viewBox="0 0 80 72" xmlns="http://www.w3.org/2000/svg">
    <rect x="36" y="4"  width="8"  height="48" rx="3" fill="#252740" stroke="#4f8ef7" stroke-width="2"/>
    <circle cx="40" cy="6" r="6" fill="#4f8ef7"/>
    <path d="M10 20 Q20 25 40 22 Q60 25 70 20"
          fill="none" stroke="#6aa3ff" stroke-width="4.5" stroke-linecap="round"/>
    <line x1="40" y1="12" x2="40" y2="22" stroke="#6aa3ff" stroke-width="2"/>
    <rect x="22" y="52" width="36" height="8"  rx="3" fill="#6aa3ff"/>
    <rect x="26" y="47" width="28" height="7"  rx="3" fill="#4f8ef7" opacity=".7"/>
    <rect x="8"  y="60" width="8"  height="10" rx="2" fill="#4f8ef7" opacity=".5"/>
    <rect x="64" y="60" width="8"  height="10" rx="2" fill="#4f8ef7" opacity=".5"/>
  </svg>`,

  'Chest Press Machine': `<svg viewBox="0 0 80 64" xmlns="http://www.w3.org/2000/svg">
    <rect x="33" y="5"  width="14" height="30" rx="3" fill="#4f8ef7"/>
    <rect x="26" y="33" width="28" height="10" rx="3" fill="#6aa3ff"/>
    <line x1="34" y1="18" x2="9"  y2="28" stroke="#4f8ef7" stroke-width="4.5" stroke-linecap="round"/>
    <circle cx="9"  cy="28" r="5.5" fill="#6aa3ff"/>
    <line x1="46" y1="18" x2="71" y2="28" stroke="#4f8ef7" stroke-width="4.5" stroke-linecap="round"/>
    <circle cx="71" cy="28" r="5.5" fill="#6aa3ff"/>
    <rect x="18" y="44" width="6" height="12" rx="2" fill="#4f8ef7" opacity=".5"/>
    <rect x="56" y="44" width="6" height="12" rx="2" fill="#4f8ef7" opacity=".5"/>
    <rect x="8"  y="54" width="64" height="8" rx="3" fill="#252740" stroke="#4f8ef7" stroke-width="1.5"/>
  </svg>`,

  'Pec Deck': `<svg viewBox="0 0 80 64" xmlns="http://www.w3.org/2000/svg">
    <rect x="36" y="8"  width="8"  height="44" rx="3" fill="#252740" stroke="#4f8ef7" stroke-width="2"/>
    <rect x="28" y="42" width="24" height="10" rx="3" fill="#6aa3ff"/>
    <line x1="37" y1="22" x2="9"  y2="15" stroke="#4f8ef7" stroke-width="4.5" stroke-linecap="round"/>
    <rect x="2"  y="10" width="8" height="20" rx="3" fill="#4f8ef7"/>
    <line x1="43" y1="22" x2="71" y2="15" stroke="#4f8ef7" stroke-width="4.5" stroke-linecap="round"/>
    <rect x="70" y="10" width="8" height="20" rx="3" fill="#4f8ef7"/>
    <rect x="8"  y="54" width="64" height="8" rx="3" fill="#252740" stroke="#4f8ef7" stroke-width="1.5"/>
  </svg>`,

  'Pull-up Bar': `<svg viewBox="0 0 80 56" xmlns="http://www.w3.org/2000/svg">
    <rect x="8"  y="10" width="6" height="42" rx="3" fill="#4f8ef7"/>
    <rect x="66" y="10" width="6" height="42" rx="3" fill="#4f8ef7"/>
    <rect x="5"  y="8"  width="70" height="9" rx="4" fill="#6aa3ff"/>
    <rect x="25" y="9"  width="4" height="7" rx="1" fill="#4f8ef7" opacity=".5"/>
    <rect x="34" y="9"  width="4" height="7" rx="1" fill="#4f8ef7" opacity=".5"/>
    <rect x="43" y="9"  width="4" height="7" rx="1" fill="#4f8ef7" opacity=".5"/>
    <rect x="52" y="9"  width="4" height="7" rx="1" fill="#4f8ef7" opacity=".5"/>
    <rect x="2"  y="50" width="18" height="5" rx="2" fill="#4f8ef7" opacity=".7"/>
    <rect x="60" y="50" width="18" height="5" rx="2" fill="#4f8ef7" opacity=".7"/>
  </svg>`,

  'Bench': `<svg viewBox="0 0 80 56" xmlns="http://www.w3.org/2000/svg">
    <rect x="4"  y="18" width="72" height="13" rx="4" fill="#6aa3ff"/>
    <rect x="8"  y="20" width="64" height="5"  rx="2" fill="#7db8ff" opacity=".35"/>
    <rect x="10" y="31" width="8"  height="20" rx="2" fill="#4f8ef7"/>
    <rect x="62" y="31" width="8"  height="20" rx="2" fill="#4f8ef7"/>
    <rect x="5"  y="49" width="22" height="5"  rx="2" fill="#4f8ef7" opacity=".7"/>
    <rect x="53" y="49" width="22" height="5"  rx="2" fill="#4f8ef7" opacity=".7"/>
  </svg>`,

  'Dip Bars': `<svg viewBox="0 0 80 56" xmlns="http://www.w3.org/2000/svg">
    <rect x="8"  y="14" width="8" height="34" rx="3" fill="#4f8ef7"/>
    <rect x="64" y="14" width="8" height="34" rx="3" fill="#4f8ef7"/>
    <rect x="4"  y="12" width="16" height="7" rx="3" fill="#6aa3ff"/>
    <rect x="60" y="12" width="16" height="7" rx="3" fill="#6aa3ff"/>
    <rect x="14" y="22" width="52" height="4" rx="2" fill="#4f8ef7" opacity=".4"/>
    <rect x="14" y="36" width="52" height="4" rx="2" fill="#4f8ef7" opacity=".4"/>
    <rect x="4"  y="46" width="20" height="5" rx="2" fill="#4f8ef7" opacity=".7"/>
    <rect x="56" y="46" width="20" height="5" rx="2" fill="#4f8ef7" opacity=".7"/>
  </svg>`,

  'Resistance Bands': `<svg viewBox="0 0 80 56" xmlns="http://www.w3.org/2000/svg">
    <ellipse cx="40" cy="28" rx="34" ry="20"
             fill="none" stroke="#4f8ef7" stroke-width="7" stroke-linecap="round"/>
    <ellipse cx="40" cy="28" rx="22" ry="12"
             fill="none" stroke="#6aa3ff" stroke-width="4" stroke-linecap="round" opacity=".4"/>
    <ellipse cx="7"  cy="28" rx="5" ry="9" fill="#4f8ef7"/>
    <ellipse cx="73" cy="28" rx="5" ry="9" fill="#4f8ef7"/>
  </svg>`,

  'Ab Wheel': `<svg viewBox="0 0 80 56" xmlns="http://www.w3.org/2000/svg">
    <rect x="4"  y="24" width="72" height="7" rx="3" fill="#4f8ef7" opacity=".45"/>
    <circle cx="40" cy="28" r="22" fill="none" stroke="#4f8ef7" stroke-width="6"/>
    <circle cx="40" cy="28" r="14" fill="none" stroke="#6aa3ff" stroke-width="1.5" opacity=".35"/>
    <circle cx="40" cy="28" r="5"  fill="#4f8ef7"/>
    <rect x="2"  y="22" width="10" height="11" rx="4" fill="#6aa3ff"/>
    <rect x="68" y="22" width="10" height="11" rx="4" fill="#6aa3ff"/>
  </svg>`,

  'Foam Roller': `<svg viewBox="0 0 80 56" xmlns="http://www.w3.org/2000/svg">
    <rect x="8"  y="14" width="64" height="28" rx="14" fill="#4f8ef7"/>
    <ellipse cx="8"  cy="28" rx="8"  ry="14" fill="#6aa3ff"/>
    <ellipse cx="72" cy="28" rx="8"  ry="14" fill="#3a7ae8"/>
    <line x1="22" y1="14" x2="22" y2="42" stroke="#6aa3ff" stroke-width="1.5" opacity=".45"/>
    <line x1="36" y1="14" x2="36" y2="42" stroke="#6aa3ff" stroke-width="1.5" opacity=".45"/>
    <line x1="50" y1="14" x2="50" y2="42" stroke="#6aa3ff" stroke-width="1.5" opacity=".45"/>
    <line x1="64" y1="14" x2="64" y2="42" stroke="#6aa3ff" stroke-width="1.5" opacity=".45"/>
  </svg>`,
};

@Component({
  selector: 'app-equipment',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './equipment.component.html',
  styleUrl: './equipment.component.scss'
})
export class EquipmentComponent implements OnInit {
  private readonly api = inject(ApiService);
  private readonly sanitizer = inject(DomSanitizer);
  protected readonly tr = inject(TranslationService);

  owned = signal<Set<string>>(new Set());
  savedItems = signal<EquipmentItem[]>([]);
  loading = signal(true);

  readonly options = EQUIPMENT_OPTIONS;
  readonly categories = [...new Set(EQUIPMENT_OPTIONS.map(o => o.category))];

  private readonly safeIcons = new Map<string, SafeHtml>(
    Object.entries(EQUIPMENT_SVGS).map(([k, v]) => [k, this.sanitizer.bypassSecurityTrustHtml(v)])
  );

  svgIcon(name: string): SafeHtml {
    return this.safeIcons.get(name) ?? this.sanitizer.bypassSecurityTrustHtml('');
  }

  optionsByCategory(cat: string): EquipmentOption[] {
    return this.options.filter(o => o.category === cat);
  }

  ngOnInit(): void {
    this.api.getEquipment().subscribe({
      next: items => {
        this.savedItems.set(items);
        this.owned.set(new Set(items.map(i => i.name)));
        this.loading.set(false);
      },
      error: () => this.loading.set(false)
    });
  }

  toggle(option: EquipmentOption): void {
    const current = new Set(this.owned());
    if (current.has(option.name)) {
      current.delete(option.name);
    } else {
      current.add(option.name);
    }
    this.owned.set(current);
    this.save(option);
  }

  isOwned(name: string): boolean {
    return this.owned().has(name);
  }

  private save(option: EquipmentOption): void {
    if (this.owned().has(option.name)) {
      this.api.addEquipment({ name: option.name, category: option.key }).subscribe(item => {
        this.savedItems.update(items => [...items, item]);
      });
    } else {
      const item = this.savedItems().find(i => i.name === option.name);
      if (item?.id) {
        this.api.deleteEquipment(item.id).subscribe(() => {
          this.savedItems.update(items => items.filter(i => i.name !== option.name));
        });
      }
    }
  }
}
