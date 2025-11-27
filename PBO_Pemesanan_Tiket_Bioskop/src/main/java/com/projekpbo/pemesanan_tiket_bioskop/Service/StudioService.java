package com.projekpbo.pemesanan_tiket_bioskop.Service;


import com.projekpbo.pemesanan_tiket_bioskop.models.Studio;
import com.projekpbo.pemesanan_tiket_bioskop.Repositories.StudioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class StudioService {

    @Autowired
    private StudioRepository studioRepository;

    public List<Studio> findAll() {
        return studioRepository.findAll();
    }

    public Studio save(Studio studio) {
        return studioRepository.save(studio);
    }

    public Optional<Studio> findById(Long id) {
        return studioRepository.findById(id);
    }
}