package com.scms.supplychainmanagementsystem.service.imp;

import com.scms.supplychainmanagementsystem.dto.PriceBookEntryDto;
import com.scms.supplychainmanagementsystem.entity.PriceBookEntry;
import com.scms.supplychainmanagementsystem.exceptions.AppException;
import com.scms.supplychainmanagementsystem.repository.PriceBookEntryRepository;
import com.scms.supplychainmanagementsystem.repository.PriceBookRepository;
import com.scms.supplychainmanagementsystem.repository.ProductRepository;
import com.scms.supplychainmanagementsystem.service.IPriceBookEntryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class PriceBookEntryService implements IPriceBookEntryService {

    private final PriceBookEntryRepository priceBookEntryRepository;
    private final PriceBookRepository priceBookRepository;
    private final ProductRepository productRepository;

    @Override
    public void updatePriceBookEntry(PriceBookEntryDto priceBookEntryDto) {
        log.info("[Start PriceBookEntryService - Update PriceBookEntry ID = " + priceBookEntryDto.getPriceBookEntryId() + "]");
        PriceBookEntry priceBookEntry = priceBookEntryRepository.findById(priceBookEntryDto.getPriceBookEntryId())
                .orElseThrow(() -> new AppException("PriceBookEntry not found"));

        priceBookEntry.setPriceBook(priceBookRepository.findById(priceBookEntryDto.getPriceBookEntryId())
                .orElseThrow(() -> new AppException("PriceBook not found")));

        priceBookEntry.setPrice(priceBookEntryDto.getPrice());

        priceBookEntry.setProduct(productRepository.findById(priceBookEntryDto.getProductId())
                .orElseThrow(() -> new AppException("Product not found")));
        log.info("[Start Save PriceBookEntry  to database]");
        priceBookEntryRepository.saveAndFlush(priceBookEntry);
        log.info("[End Save PriceBookEntry  to database]");
        log.info("[End PriceBookEntryService -  Update PriceBookEntry ID = " + priceBookEntryDto.getPriceBookEntryId() + "]");

    }

    @Override
    public void createPriceBookEntry(PriceBookEntryDto priceBookEntryDto) {
        log.info("[Start PriceBookEntryService - Create PriceBookEntry ]");
        PriceBookEntry priceBookEntry = new PriceBookEntry();
        priceBookEntry.setPriceBook(priceBookRepository.findById(priceBookEntryDto.getPriceBookId())
                .orElseThrow(() -> new AppException("PriceBook not found")));

        priceBookEntry.setPrice(priceBookEntryDto.getPrice());

        priceBookEntry.setProduct(productRepository.findById(priceBookEntryDto.getProductId())
                .orElseThrow(() -> new AppException("Product not found")));
        log.info("[Start Save PriceBookEntry to database]");
        priceBookEntryRepository.saveAndFlush(priceBookEntry);
        log.info("[End Save PriceBookEntry to database]");
        log.info("[End PriceBookEntryService - Create PriceBookEntry ]");
    }

    @Override
    public void deletePriceBookEntryById(Long priceBookEntryId) {
        log.info("[Start PriceBookEntryService - Delete PriceBookEntry ID = : " + priceBookEntryId + "]");
        priceBookEntryRepository.deleteById(priceBookEntryRepository.findById(priceBookEntryId)
                .orElseThrow(() -> new AppException("PriceBook Entry not found")).getPriceBookEntryID());
        log.info("[End PriceBookEntryService - Delete PriceBookEntry ID = : " + priceBookEntryId + "]");
    }

    @Override
    public PriceBookEntryDto getPriceBookEntryById(Long priceBookEntryId) {
        log.info("[Start PriceBookEntryService - Get PriceBookEntry ID = : " + priceBookEntryId + "]");
        PriceBookEntry priceBookEntry = priceBookEntryRepository.findById(priceBookEntryId)
                .orElseThrow(() -> new AppException("PriceBookEntry not found"));

        PriceBookEntryDto priceBookEntryDto = new PriceBookEntryDto();
        priceBookEntryDto.setPriceBookEntryId(priceBookEntry.getPriceBookEntryID());
        priceBookEntryDto.setProductId(priceBookEntry.getProduct().getProductId());
        priceBookEntryDto.setPrice(priceBookEntry.getPrice());

        log.info("[End  PriceBookEntryService - Get PriceBookEntry ID = : " + priceBookEntryId + "]");
        return priceBookEntryDto;
    }

    @Override
    public List<PriceBookEntryDto> getAllPriceBookEntriesByProductId(Long productId) {
        log.info("[Start PriceBookEntryService - Get All PriceBook Entries ByProductId]");
        List<PriceBookEntry> priceBookEntryList = priceBookEntryRepository.findAllByProductId(productId);
        List<PriceBookEntryDto> priceBookEntryDtoList =
                priceBookEntryList.stream().map(x -> new PriceBookEntryDto(x.getPriceBookEntryID()
                        , x.getPrice()
                        , x.getPriceBook().getPriceBookId()
                        , x.getProduct().getProductId())).collect(Collectors.toList());
        log.info("[End PriceBookEntryService - Get All PriceBook Entries ByProductId]");
        return priceBookEntryDtoList;
    }

    @Override
    public Page<PriceBookEntry> getAllPriceBookEntriesByPriceBookId(Long priceBookId, Pageable pageable) {
        log.info("[Start PriceBookEntryService - Get All PriceBook Entries By PriceBookId]");
        Page<PriceBookEntry> priceBookEntryPage = priceBookEntryRepository.findAllByPriceBookId(priceBookId, pageable);
        log.info("[End PriceBookEntryService - Get All PriceBook Entries By PriceBookId]");
        return priceBookEntryPage;
    }

}
