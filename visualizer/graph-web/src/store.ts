import { create } from 'zustand';
import { parseGraphFile, type GraphFile, type HoveredEdge } from './lib/graph';

interface VizState {
  graph: GraphFile | null;
  graphName: string;
  selectedId: number | null;
  hoveredId: number | null;
  hoveredEdge: HoveredEdge | null;
  availableGraphs: string[];

  loadGraphFile: (raw: unknown, name: string) => void;
  select: (id: number | null) => void;
  hover: (id: number | null) => void;
  hoverEdge: (edge: HoveredEdge | null) => void;
  setAvailableGraphs: (names: string[]) => void;
}

export const useStore = create<VizState>((set) => ({
  graph: null,
  graphName: '',
  selectedId: null,
  hoveredId: null,
  hoveredEdge: null,
  availableGraphs: [],

  loadGraphFile: (raw, name) => {
    set({
      graph: parseGraphFile(raw),
      graphName: name,
      selectedId: null,
      hoveredId: null,
      hoveredEdge: null,
    });
  },
  select: (id) => set({ selectedId: id }),
  hover: (id) => set({ hoveredId: id }),
  hoverEdge: (edge) => set({ hoveredEdge: edge }),
  setAvailableGraphs: (names) => set({ availableGraphs: names }),
}));
