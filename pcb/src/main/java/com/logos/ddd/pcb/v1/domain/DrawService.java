package com.logos.ddd.pcb.v1.domain;

public class DrawService {

    private final NetRepository netRepository;
    private final ChipRepository chipRepository;

    public DrawService(NetRepository netRepository, ChipRepository chipRepository) {
        this.netRepository = netRepository;
        this.chipRepository = chipRepository;
    }


    public Net linkChip(Long startChipId, Long endChipId) {
        Net net = new Net();
        Chip startChip = chipRepository.find(startChipId);
        Chip endChip = chipRepository.find(endChipId);
        net.setStartChip(startChip);
        net.setEndChip(endChip);
        return netRepository.save(net);
    }
}
