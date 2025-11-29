package com.projekpbo.pemesanan_tiket_bioskop.Service;

import com.projekpbo.pemesanan_tiket_bioskop.models.Kursi;
import com.projekpbo.pemesanan_tiket_bioskop.Repositories.KursiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class KursiService {

    @Autowired
    private KursiRepository kursiRepository;

    public List<Kursi> findAllById(List<Long> ids) {
        return kursiRepository.findAllById(ids);
    }

    public Kursi save(Kursi kursi) {
        return kursiRepository.save(kursi);
    }

    public void deleteAllByStudioId(Long studioId) {
        kursiRepository.deleteByStudioId(studioId);
    }
}