package com.logos.ddd.pcb.v1.domain;

public class DrawService {

    private final NetRepository netRepository;
    private final ChipRepository chipRepository;

    public DrawService(NetRepository netRepository, ChipRepository chipRepository) {
        this.netRepository = netRepository;
        this.chipRepository = chipRepository;
    }

    public Net linkChip(Net net, Chip startChip, Chip endChip) {
        net.setStartChip(startChip);
        net.setEndChip(endChip);
        return netRepository.save(net);
    }
}
