package com.scms.supplychainmanagementsystem.service.imp;

import com.scms.supplychainmanagementsystem.common.UserCommon;
import com.scms.supplychainmanagementsystem.dto.PriceBookDto;
import com.scms.supplychainmanagementsystem.entity.PriceBook;
import com.scms.supplychainmanagementsystem.entity.User;
import com.scms.supplychainmanagementsystem.entity.Warehouse;
import com.scms.supplychainmanagementsystem.exceptions.AppException;
import com.scms.supplychainmanagementsystem.repository.PriceBookRepository;
import com.scms.supplychainmanagementsystem.repository.WarehouseRepository;
import com.scms.supplychainmanagementsystem.service.IPriceBookService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class PriceBookService implements IPriceBookService {

    private final PriceBookRepository priceBookRepository;
    private final UserCommon userCommon;
    private final WarehouseRepository warehouseRepository;

    @Override
    public void updatePriceBook(PriceBookDto priceBookDto) {
        log.info("[Start PriceBookService - Update PriceBook : " + priceBookDto.getPriceBookName() + "]");
        if (checkAccessPriceBook(priceBookDto.getPriceBookId())) {
            PriceBook priceBook = priceBookRepository.findById(priceBookDto.getPriceBookId())
                    .orElseThrow(() -> new AppException("PriceBook Not Found"));
            priceBook.setPriceBookName(priceBookDto.getPriceBookName());
            priceBook.setIsStandardPriceBook(priceBookDto.getIsActive());
            checkStandardPriceBook();
            priceBook.setIsActive(priceBookDto.getIsActive());
            User current = userCommon.getCurrentUser();

            if (current.getRole().getRoleID() == 1) {
                priceBook.setWarehouse(warehouseRepository.getById(priceBookDto.getWarehouseId()));
            } else {
                priceBook.setWarehouse(current.getWarehouse());
            }
            log.info("[Start Save PriceBook " + priceBookDto.getPriceBookName() + " to database]");
            priceBookRepository.saveAndFlush(priceBook);
            log.info("[End Save PriceBook " + priceBookDto.getPriceBookName() + " to database]");
            log.info("[End PriceBookService - Update PriceBook : " + priceBookDto.getPriceBookName() + "]");
        } else {
            throw new AppException("Not allow to access this resource");
        }
    }

    @Override
    public void createPriceBook(PriceBookDto priceBookDto) {
        log.info("[Start PriceBookService - Create PriceBook : " + priceBookDto.getPriceBookName() + "]");
        User current = userCommon.getCurrentUser();
        PriceBook priceBook = new PriceBook();
        priceBook.setPriceBookName(priceBookDto.getPriceBookName());
        priceBook.setIsStandardPriceBook(priceBookDto.getIsActive());
        checkStandardPriceBook();
        priceBook.setIsActive(priceBookDto.getIsActive());

        if (current.getRole().getRoleID() == 1) {
            priceBook.setWarehouse(warehouseRepository.findById(priceBookDto.getWarehouseId())
                    .orElseThrow(() -> new AppException("Warehouse not found")));
        } else {
            priceBook.setWarehouse(current.getWarehouse());
        }
        log.info("[Start Save PriceBook " + priceBookDto.getPriceBookName() + " to database]");
        priceBookRepository.saveAndFlush(priceBook);
        log.info("[End Save PriceBook " + priceBookDto.getPriceBookName() + " to database]");
        log.info("[End PriceBookService - Create PriceBook : " + priceBookDto.getPriceBookName() + "]");
    }

    @Override
    public void deletePriceBookById(Long priceBookId) {
        log.info("[Start PriceBookService - Delete PriceBook ID = : " + priceBookId + "]");
        if (checkAccessPriceBook(priceBookId)) {
            priceBookRepository.deleteById(priceBookId);
        } else {
            throw new AppException("Not allow to delete this resource");
        }
        log.info("[End PriceBookService - Delete PriceBook ID = : " + priceBookId + "]");
    }

    @Override
    public PriceBookDto getPriceBookById(Long priceBookId) {
        log.info("[Start PriceBookService - Get PriceBook ID = : " + priceBookId + "]");
        PriceBookDto priceBookDto = new PriceBookDto();
        PriceBook priceBook = priceBookRepository.findById(priceBookId)
                .orElseThrow(() -> new AppException("PriceBook Not Found"));
        if (checkAccessPriceBook(priceBookId)) {
            priceBookDto.setPriceBookId(priceBookId);
            priceBookDto.setPriceBookName(priceBook.getPriceBookName());
            priceBookDto.setWarehouseId(priceBook.getWarehouse().getWarehouseID());
            priceBook.setIsStandardPriceBook(priceBookDto.getIsActive());
            priceBook.setIsActive(priceBookDto.getIsActive());
        } else {
            throw new AppException("Not allow to access this resource");
        }
        log.info("[End  PriceBookService - Get PriceBook ID = : " + priceBookId + "]");
        return priceBookDto;
    }

    @Override
    public Page<PriceBook> getAllPriceBooks(String priceBookName, Long warehouseId, Pageable pageable) {
        log.info("[Start PriceBookService - Get All PriceBooks]");
        Page<PriceBook> categoryPage;
        User current = userCommon.getCurrentUser();
        Warehouse wh = current.getWarehouse();
        if (current.getRole().getRoleID() == 1) {
            categoryPage = priceBookRepository.filterAllWarehouses(priceBookName, warehouseId, pageable);
        } else {
            categoryPage = priceBookRepository.filterInOneWarehouse(priceBookName, wh.getWarehouseID(), pageable);
        }
        log.info("[End PriceBookService - Get All PriceBooks]");
        return categoryPage;
    }

    public boolean checkAccessPriceBook(Long priceBookId) {
        PriceBook priceBook = priceBookRepository.findById(priceBookId)
                .orElseThrow(() -> new AppException("PriceBook Not Found"));
        User current = userCommon.getCurrentUser();
        if (current.getRole().getRoleID() == 1) {
            return true;
        }
        if (priceBook.getWarehouse() != null && current.getWarehouse() != null) {
            return priceBook.getWarehouse().getWarehouseID().equals(current.getWarehouse().getWarehouseID());
        }
        return false;
    }

    public void checkStandardPriceBook() {
        if (priceBookRepository.existsByIsStandardPriceBook(true)) {
            throw new AppException("Exist Standard PriceBook");
        }
    }
}
